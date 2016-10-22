package de.daxu.swamp.scheduler.action;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.client.DockerClientFactory;
import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.manager.SchedulingManager;

import java.util.Date;
import java.util.Set;

public class SyncContainerInstanceAction extends Action {

    public SyncContainerInstanceAction( EventHandler eventHandler, SchedulingManager schedulingManager ) {
        super( eventHandler, schedulingManager );
    }

    @Override
    public void execute() {
        Set<ContainerInstance> instances = getSchedulingManager().getAllInstances();

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
