package de.daxu.swamp.core.strategy;

import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.server.Server;

import java.util.Optional;
import java.util.Set;

public interface ServerLocatorStrategy {

    Optional<Server> locate( Set<Location> locations );
}
