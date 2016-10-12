package de.daxu.swamp.core.scheduler.action;

import com.github.dockerjava.api.DockerClient;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.client.DockerClientFactory;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ClearNotManagedContainers implements SchedulerAction {

    @Autowired
    LocationService locationService;

    @Override
    public void execute( Scheduler scheduler ) {
        List<Server> servers = locationService.getAllServers();
        Map<String, ContainerInstance> containerMap = scheduler.getInternalMap();

        servers.forEach( server -> {
            DockerClient dockerClient = DockerClientFactory.createClient( server );

            List<com.github.dockerjava.api.model.Container> runningContainers = dockerClient.listContainersCmd().withShowAll( true ).exec();
            runningContainers.forEach( runningContainer -> {
                if ( runningContainer.getImage().equalsIgnoreCase( "swarm" ) ) return;
                if ( containerMap.get( runningContainer.getId() ) == null ) {
                    if ( runningContainer.getStatus().contains( "Up" ) )
                        dockerClient.killContainerCmd( runningContainer.getId() ).exec();
                    dockerClient.removeContainerCmd( runningContainer.getId() ).exec();
                }
            } );
        } );
    }
}
