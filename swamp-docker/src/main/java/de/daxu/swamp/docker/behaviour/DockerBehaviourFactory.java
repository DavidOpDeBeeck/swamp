package de.daxu.swamp.docker.behaviour;

import de.daxu.swamp.core.server.Server;

public interface DockerBehaviourFactory {

    DockerBehaviour createBehaviour(Server server);

}
