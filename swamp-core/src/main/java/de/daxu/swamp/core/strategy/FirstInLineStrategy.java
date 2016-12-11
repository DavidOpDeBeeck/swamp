package de.daxu.swamp.core.strategy;

import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.server.Server;

import java.util.Optional;
import java.util.Set;

public class FirstInLineStrategy implements ServerLocatorStrategy {

    @Override
    public Optional<Server> locate( Set<Location> locations ) {
        if( locations == null ) return Optional.empty();
        return locations.stream()
                .map( Location::getServers )
                .flatMap( Set::stream )
                .findFirst();
    }
}
