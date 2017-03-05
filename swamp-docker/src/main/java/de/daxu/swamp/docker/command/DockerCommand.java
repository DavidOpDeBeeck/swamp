package de.daxu.swamp.docker.command;

import de.daxu.swamp.docker.client.DockerClient;

@FunctionalInterface
public interface DockerCommand {

    void execute(DockerClient client);
}
