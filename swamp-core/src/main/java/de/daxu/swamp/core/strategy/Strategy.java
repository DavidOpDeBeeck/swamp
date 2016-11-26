package de.daxu.swamp.core.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;

import java.util.Optional;

public interface Strategy {

    Optional<Server> calculate( Container container );
}
