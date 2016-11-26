package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.*;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    public final static String SCHEDULING_URL = "/scheduling";

    private final ContainerInstanceWriteService containerInstanceWriteService;
    private final ProjectService projectService;

    @Autowired
    public SchedulingResource( ContainerInstanceWriteService containerInstanceWriteService,
                               ProjectService projectService ) {
        this.containerInstanceWriteService = containerInstanceWriteService;
        this.projectService = projectService;
    }

    @RequestMapping( value = "/container/{containerId}", params = "action=schedule", method = RequestMethod.GET )
    public void schedule( @PathVariable( value = "containerId" ) String containerId ) {
        Container container = projectService.getContainer( containerId );

        Set<Server> potentialServers = getPotentialServers( container );

        if( !potentialServers.isEmpty() ) {
            containerInstanceWriteService.schedule( container, potentialServers.iterator().next() );
        }
    }

    private Set<Server> getPotentialServers( Container container ) {
        Set<Location> locations = container.getPotentialLocations();

        if( locations == null ) return new HashSet<>();

        Set<Continent> continents = getLocationsWithType( locations, LocationType.CONTINENT, Continent.class );
        Set<Datacenter> datacenters = getLocationsWithType( locations, LocationType.DATACENTER, Datacenter.class );
        Set<Server> servers = getLocationsWithType( locations, LocationType.SERVER, Server.class );

        continents.forEach( continent -> datacenters.addAll( continent.getDatacenters() ) );
        datacenters.forEach( datacenter -> servers.addAll( datacenter.getServers() ) );

        return servers;
    }

    private <T extends Location> Set<T> getLocationsWithType( Set<Location> locations, LocationType type, Class<T> clazz ) {
        return locations.stream()
                .filter( location -> location.getType() == type )
                .map( clazz::cast )
                .collect( Collectors.toSet() );
    }
}
