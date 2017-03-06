package de.daxu.swamp.docker.behaviour;

import de.daxu.swamp.core.server.Server;

public interface DockerBehaviour
        extends DockerContainerBehaviour, DockerNetworkBehaviour {

    Server getServer();
}
