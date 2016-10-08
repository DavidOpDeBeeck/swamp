package de.daxu.swamp.core.scheduler;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.config.ServerSSLConfig;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static de.daxu.swamp.core.scheduler.ContainerInstance.ContainerInstanceBuilder.aContainerInstance;

@Component
public class SchedulerImpl implements Scheduler {

    private Map<String, ContainerInstance> containersMap = new HashMap<>();

    @Override
    public void schedule( Project project, SchedulingStrategy strategy ) {
        List<ContainerInstance> instances = new ArrayList<>();

        project.getContainers()
                .stream()
                .forEach( container -> {
                    Server server = getServerFromContainer( container );

                    if ( server == null )
                        return;

                    DockerClient dockerClient = createDockerClient( server );

                    clearRunningContainers( dockerClient );

                    List<String> containerIds = container.getRunConfiguration().execute( dockerClient );
                    dockerClient.startContainerCmd( containerIds.get( 0 ) ).exec();

                    instances.addAll( containerIds.stream().map( id -> aContainerInstance()
                            .withInternalContainerId( id )
                            .withProject( project )
                            .withContainer( container )
                            .withStartDate( new Date() )
                            .build() )
                            .collect( Collectors.toList() ) );

                    instances
                            .stream()
                            .forEach( instance -> containersMap.put( instance.getInternalContainerId(), instance ) );
                } );
    }

    @Override
    public Collection<ContainerInstance> getInstances( Project project ) {
        return containersMap.values();
    }

    @Scheduled( fixedRate = 10000 )
    public void syncContainerInstanceStatus() {
        containersMap.values().forEach( instance -> {
            DockerClient dockerClient = createDockerClient( getServerFromContainer( instance.getContainer() ) );

            InspectContainerResponse response = dockerClient.inspectContainerCmd( instance.getInternalContainerId() ).exec();
            InspectContainerResponse.ContainerState state = response.getState();

            dockerClient.logContainerCmd( instance.getInternalContainerId() )
                    .withStdErr( true )
                    .withStdOut( true )
                    .exec( new ResultCallbackTemplate<ResultCallback<Frame>, Frame>() {
                        @Override
                        public void onNext( Frame frame ) {
                            instance.addLog( frame.toString().replaceAll( "STDOUT:", "\n" ) );
                        }
                    } );

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

    private Server getServerFromContainer( Container container ) {
        return ( Server ) container.getPotentialLocations()
                .stream()
                .filter( l -> l instanceof Server )
                .findFirst()
                .orElse( null );
    }

    private void clearRunningContainers( DockerClient dockerClient ) {
        List<com.github.dockerjava.api.model.Container> runningContainers = dockerClient.listContainersCmd().withShowAll( true ).exec();
        runningContainers.forEach( runningContainer -> {
            if ( runningContainer.getImage().equalsIgnoreCase( "swarm" ) ) return;
            if ( runningContainer.getStatus().contains( "Up" ) )
                dockerClient.killContainerCmd( runningContainer.getId() ).exec();
            dockerClient.removeContainerCmd( runningContainer.getId() ).exec();
        } );
    }

    private DockerClient createDockerClient( Server server ) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost( "tcp://docker.daxu.de:3376" )
                .withDockerTlsVerify( true )
                .withCustomSslConfig( new ServerSSLConfig( server ) )
                .withApiVersion( "1.24" )
                .build();
        return DockerClientBuilder.getInstance( config ).build();
    }

}
