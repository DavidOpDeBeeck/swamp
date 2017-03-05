package de.daxu.swamp.docker.client;

import de.daxu.swamp.core.server.Server;

public interface DockerClientFactory {

    DockerClient createClient(Server server);

}
