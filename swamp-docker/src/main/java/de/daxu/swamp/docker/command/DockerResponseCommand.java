package de.daxu.swamp.docker.command;

import de.daxu.swamp.docker.client.DockerClient;

@FunctionalInterface
public interface DockerResponseCommand<T> {

    T execute(DockerClient client);
}
