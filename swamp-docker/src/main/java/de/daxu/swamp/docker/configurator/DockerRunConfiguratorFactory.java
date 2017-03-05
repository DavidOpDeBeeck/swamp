package de.daxu.swamp.docker.configurator;

import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.docker.client.DockerClient;
import de.daxu.swamp.workspace.manager.WorkspaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerRunConfiguratorFactory {

    private final WorkspaceManager workspaceManager;

    @Autowired
    public DockerRunConfiguratorFactory(WorkspaceManager workspaceManager) {
        this.workspaceManager = workspaceManager;
    }

    public DockerRunConfigurator create(DockerClient client, Notifier<String> notifier) {
        return new DockerRunConfigurator(client, workspaceManager, notifier);
    }
}
