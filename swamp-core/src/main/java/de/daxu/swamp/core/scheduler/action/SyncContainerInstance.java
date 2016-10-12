package de.daxu.swamp.core.scheduler.action;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.client.DockerClientFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class SyncContainerInstance implements SchedulerAction {

    @Override
    public void execute( Scheduler scheduler ) {
        Map<String, ContainerInstance> containerMap = scheduler.getInternalMap();

        containerMap.values().forEach( instance -> {
            DockerClient dockerClient = DockerClientFactory.createClient( instance.getServer() );

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

            containerMap.put( instance.getInternalContainerId(), instance );
        } );

        scheduler.updateInternalMap( containerMap );
    }
}
