package de.daxu.swamp.docker.client;

import de.daxu.swamp.core.server.Server;

public interface DockerClient
        extends DockerContainerBehaviour, DockerNetworkClientBehaviour {

    Server getServer();
}
