package de.daxu.swamp.core.scheduler.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.LocationType;
import de.daxu.swamp.core.location.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FairStrategy implements SchedulingStrategy {

    @Override
    public Map<Container, Server> createSchedule( List<Container> containers ) {
        Map<Container, Server> schedule = new HashMap<>();
        //TODO: this isn't a fair strategy
        containers.stream()
                .forEach( container -> {
                    Set<Server> potentialServers = getPotentialServers( container );

                    if ( potentialServers != null )
                        schedule.put( container, potentialServers.iterator().next() );
                } );
        return schedule;
    }

    private Set<Server> getPotentialServers( Container container ) {
        List<Location> potentialLocations = container.getPotentialLocations();

        if ( potentialLocations == null )
            return null;

        return potentialLocations.stream()
                .filter( location -> location.getType() == LocationType.SERVER )
                .map( Server.class::cast )
                .collect( Collectors.toSet() );
    }
}
