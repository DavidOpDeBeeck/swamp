package de.daxu.swamp.docker.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployResult;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;

import static com.google.common.collect.Sets.newHashSet;

public class DockerCommandExecutor {

    private final DockerBehaviour behaviour;

    public DockerCommandExecutor(DockerBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public DeployResult<Void> execute(DockerCommand command) {
        return catchWarnings((client) -> {
            command.execute(client);
            return null;
        });
    }

    public <T> DeployResult<T> executeWithResponse(DockerResponseCommand<T> command) {
        return catchWarnings(command);
    }

    private <T> DeployResult<T> catchWarnings(DockerResponseCommand<T> command) {
        try {
            T result = command.execute(client());
            return DeployResult.result(result);
        } catch (Exception e) {
            String error = String.format("Exception %s: %s", e.getClass().getSimpleName(), e.getMessage());
            return DeployResult.warnings(newHashSet(error));
        }
    }

    private DockerBehaviour client() {
        return behaviour;
    }

    public Server getServer() {
        return behaviour.getServer();
    }
}
