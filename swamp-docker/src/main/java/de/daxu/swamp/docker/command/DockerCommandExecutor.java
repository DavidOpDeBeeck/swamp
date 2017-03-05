package de.daxu.swamp.docker.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.result.DeployResult;
import de.daxu.swamp.docker.client.DockerClient;

import static com.google.common.collect.Sets.newHashSet;

public class DockerCommandExecutor {

    private final DockerClient client;

    public DockerCommandExecutor(DockerClient client) {
        this.client = client;
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

    private DockerClient client() {
        return client;
    }

    public Server getServer() {
        return client.getServer();
    }
}
