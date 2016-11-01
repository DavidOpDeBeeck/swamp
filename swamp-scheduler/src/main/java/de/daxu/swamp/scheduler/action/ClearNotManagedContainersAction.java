package de.daxu.swamp.scheduler.action;

import com.github.dockerjava.api.DockerClient;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.scheduling.SchedulingService;
import de.daxu.swamp.service.LocationService;

import java.util.List;

import static de.daxu.swamp.scheduler.client.DockerClientFactory.*;

public class ClearNotManagedContainersAction extends Action {

    private LocationService locationService;

    public ClearNotManagedContainersAction( EventHandler eventHandler, SchedulingService schedulingService, LocationService locationService ) {
        super( eventHandler, schedulingService );
        this.locationService = locationService;
    }

    @Override
    public void execute() {
        List<Server> servers = locationService.getAllServers();

        servers.forEach( server -> {
            DockerClient dockerClient = createClient( server );

            List<com.github.dockerjava.api.model.Container> runningContainers = dockerClient.listContainersCmd().withShowAll( true ).exec();
            runningContainers.forEach( runningContainer -> {
                if ( runningContainer.getImage().equalsIgnoreCase( "swarm" ) ) return;
                if ( getSchedulingService().getContainerInstanceByInternalId( runningContainer.getId() ) == null ) {
                    if ( runningContainer.getStatus().contains( "Up" ) )
                        dockerClient.killContainerCmd( runningContainer.getId() ).exec();
                    dockerClient.removeContainerCmd( runningContainer.getId() ).exec();
                }
            } );
        } );
    }
}
