package de.daxu.swamp.docker.command;

import de.daxu.swamp.docker.behaviour.DockerBehaviour;

@FunctionalInterface
public interface DockerCommand {

    void execute(DockerBehaviour client);
}
