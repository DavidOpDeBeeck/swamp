package de.daxu.swamp.docker.command;

import de.daxu.swamp.docker.behaviour.DockerBehaviour;

@FunctionalInterface
public interface DockerResponseCommand<T> {

    T execute(DockerBehaviour client);
}
