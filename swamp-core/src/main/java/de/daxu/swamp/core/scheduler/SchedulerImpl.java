package de.daxu.swamp.core.scheduler;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.action.SchedulerAction;
import de.daxu.swamp.core.scheduler.client.DockerClientFactory;
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

    @Autowired
    List<SchedulerAction> actions;

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

    @Override
    public synchronized void updateInternalMap( Map<String, ContainerInstance> map ) {
        containersMap = map;
    }

    @Override
    public Map<String, ContainerInstance> getInternalMap() {
        return containersMap;
    }

    @Scheduled( fixedRate = 10000 )
    private void executeActions() {
        actions.forEach( a -> a.execute( this ) );
    }

    private void startContainer( Project project, Container container, Server server ) {
        DockerClient dockerClient = DockerClientFactory.createClient( server );

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