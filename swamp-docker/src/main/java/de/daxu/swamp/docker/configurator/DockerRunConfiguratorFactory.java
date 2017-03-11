package de.daxu.swamp.docker.configurator;

import de.daxu.swamp.deploy.notifier.DeployNotifier;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;
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

    public DockerRunConfigurator create(DockerBehaviour client, DeployNotifier<String> notifier) {
        return new DockerRunConfigurator(client, workspaceManager, notifier);
    }
}
