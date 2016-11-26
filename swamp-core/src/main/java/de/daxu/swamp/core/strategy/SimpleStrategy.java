package de.daxu.swamp.core.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleStrategy implements Strategy {

    @Override
    public Optional<Server> calculate( Container container ) {
        Set<Server> servers = getPotentialServers( container );
        return servers.isEmpty() ? Optional.empty() : Optional.of( servers.iterator().next() );
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
