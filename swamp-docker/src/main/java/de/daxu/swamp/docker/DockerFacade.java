package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.group.GroupManagerImpl;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.workspace.WorkspaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerFacade implements DeployFacade {

    private final GroupManager groupManager;
    private final WorkspaceManager workspaceManager;
    private final DockerClientFactory dockerClientFactory;
    private final ContainerResultFactory containerResultFactory;

    @Autowired
    public DockerFacade(WorkspaceManager workspaceManager,
                        DockerClientFactory dockerClientFactory,
                        ContainerResultFactory containerResultFactory) {
        this.workspaceManager = workspaceManager;
        this.groupManager = new GroupManagerImpl();
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
    }

    @Override
    public ContainerClient containerClient(Server server) {
        return new DockerContainerClient(
                workspaceManager,
                groupManager,
                dockerClientFactory,
                containerResultFactory,
                server);
    }

    @Override
    public GroupManager groupManager() {
        return groupManager;
    }

}
