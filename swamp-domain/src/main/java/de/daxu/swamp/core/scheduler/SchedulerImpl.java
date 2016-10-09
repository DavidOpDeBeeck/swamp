package de.daxu.swamp.core.scheduler;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.config.ServerSSLConfig;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static de.daxu.swamp.core.scheduler.ContainerInstance.ContainerInstanceBuilder.aContainerInstance;

@Component
public class SchedulerImpl implements Scheduler {

    @Autowired
    LocationService locationService;

    private Map<String, ContainerInstance> containersMap = new HashMap<>();

    @Override
    public void schedule( Project project, SchedulingStrategy strategy ) {
        Map<Container, Server> schedule = strategy.createSchedule( project.getContainers() );
        schedule.entrySet().stream().forEach( entry -> startContainer( project, entry.getKey(), entry.getValue() ) );
    }

    @Override
    public Collection<Project> getProjects() {
        return containersMap.values()
                .stream()
                .map( ContainerInstance::getProject )
                .collect( Collectors.toSet() );
    }

    @Override
    public Collection<ContainerInstance> getInstances( Project project ) {
        return containersMap.values()
                .stream()
                .filter( containerInstance -> containerInstance.getProject().getId().equals( project.getId() ) )
                .collect( Collectors.toList() );
    }

    private void startContainer( Project project, Container container, Server server ) {
        DockerClient dockerClient = createDockerClient( server );

        CreateContainerCmd createContainerCmd = container.getRunConfiguration().execute( dockerClient );
        // TODO: set ports or something else
        CreateContainerResponse response = createContainerCmd.exec();

        dockerClient.startContainerCmd( response.getId() ).exec();
        dockerClient.logContainerCmd( response.getId() )
                .withStdErr( true )
                .withStdOut( true )
                .withFollowStream( true )
                .exec( new LogSyncCallback( response.getId() ) );

        ContainerInstance instance = aContainerInstance()
                .withInternalContainerId( response.getId() )
                .withServer( server )
                .withProject( project )
                .withContainer( container )
                .withStartDate( new Date() )
                .build();

        containersMap.put( instance.getInternalContainerId(), instance );
    }

    @Scheduled( fixedRate = 10000 )
    public void syncContainerInstanceStatus() {
        containersMap.values().forEach( instance -> {
            DockerClient dockerClient = createDockerClient( instance.getServer() );

            InspectContainerResponse response = dockerClient.inspectContainerCmd( instance.getInternalContainerId() ).exec();
            InspectContainerResponse.ContainerState state = response.getState();

            if ( state.getRunning() )
                instance.setStatus( ContainerInstance.Status.RUNNING );
            else if ( state.getPaused() )
                instance.setStatus( ContainerInstance.Status.PAUSED );
            else if ( state.getRestarting() )
                instance.setStatus( ContainerInstance.Status.RESTARTING );
            else {
                instance.setStatus( ContainerInstance.Status.EXITED );
                instance.setFinishedAt( new Date() );
            }

            containersMap.put( instance.getInternalContainerId(), instance );
        } );
    }

    @Scheduled( fixedRate = 10000 )
    private void clearNotManagedContainers() {
        List<Server> servers = locationService.getAllServers();

        servers.forEach( server -> {
            DockerClient dockerClient = createDockerClient( server );

            List<com.github.dockerjava.api.model.Container> runningContainers = dockerClient.listContainersCmd().withShowAll( true ).exec();
            runningContainers.forEach( runningContainer -> {
                if ( runningContainer.getImage().equalsIgnoreCase( "swarm" ) ) return;
                if ( containersMap.get( runningContainer.getId() ) == null ) {
                    if ( runningContainer.getStatus().contains( "Up" ) )
                        dockerClient.killContainerCmd( runningContainer.getId() ).exec();
                    dockerClient.removeContainerCmd( runningContainer.getId() ).exec();
                }
            } );
        } );
    }

    private DockerClient createDockerClient( Server server ) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost( server.getIp() )
                .withDockerTlsVerify( true )
                .withCustomSslConfig( new ServerSSLConfig( server ) )
                .withApiVersion( "1.24" )
                .build();
        return DockerClientBuilder.getInstance( config ).build();
    }

    private class LogSyncCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

        private String internalContainerId;

        LogSyncCallback( String internalContainerId ) {
            this.internalContainerId = internalContainerId;
        }

        @Override
        public void onNext( Frame object ) {
            ContainerInstance instance = containersMap.get( internalContainerId );
            instance.addLog( object.toString().replaceAll( "STDOUT: ", "\n" ).replaceAll( "STDERR: ", "\n" ) );
            containersMap.put( internalContainerId, instance );
        }
    }
}
