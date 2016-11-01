package de.daxu.swamp.scheduler.action;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.client.DockerClientFactory;
import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.scheduling.SchedulingService;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class SyncContainerInstanceAction extends Action {

    public SyncContainerInstanceAction( EventHandler eventHandler, SchedulingService schedulingService ) {
        super( eventHandler, schedulingService );
    }

    @Override
    public void execute() {
        Set<ContainerInstance> instances = getSchedulingService().getAllProjectInstances()
                .stream()
                .flatMap( p -> p.getContainerInstances().stream() )
                .collect( Collectors.toSet() );

        instances.forEach( instance -> {
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

            getEventHandler().onUpdate( instance );
        } );
    }
}
