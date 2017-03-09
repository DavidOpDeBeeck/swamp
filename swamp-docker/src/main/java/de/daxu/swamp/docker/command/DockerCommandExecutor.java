package de.daxu.swamp.docker.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployResult;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.google.common.collect.Sets.newHashSet;

public class DockerCommandExecutor {

    private final DockerBehaviour behaviour;

    public DockerCommandExecutor(DockerBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public DeployResult<Void> action(Consumer<DockerBehaviour> command) {
        return executeAndCatchWarnings(toFunction(command));
    }

    public <T> DeployResult<T> result(Function<DockerBehaviour, T> command) {
        return executeAndCatchWarnings(command);
    }

    private <T> DeployResult<T> executeAndCatchWarnings(Function<DockerBehaviour, T> command) {
        try {
            T result = command.apply(behaviour());
            return DeployResult.result(result);
        } catch (Exception e) {
            String error = String.format("Exception %s: %s", e.getClass().getSimpleName(), e.getMessage());
            return DeployResult.warnings(newHashSet(error));
        }
    }

    private DockerBehaviour behaviour() {
        return behaviour;
    }

    private Function<DockerBehaviour, Void> toFunction(Consumer<DockerBehaviour> command) {
        return client -> {
            command.accept(client);
            return null;
        };
    }

    public Server getServer() {
        return behaviour.getServer();
    }
}
