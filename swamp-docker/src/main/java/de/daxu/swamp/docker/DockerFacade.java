package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.docker.client.DockerClient;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.command.DockerCommandExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerFacade implements DeployFacade {


    private final GroupManager groupManager;
    private final DockerClientFactory dockerClientFactory;

    @Autowired
    public DockerFacade(GroupManager groupManager,
                        DockerClientFactory dockerClientFactory) {
        this.groupManager = groupManager;
        this.dockerClientFactory = dockerClientFactory;
    }

    @Override
    public ContainerClient containerClient(Server server) {
        DockerClient client = dockerClientFactory.createClient(server);
        return new DockerContainerClient(new DockerCommandExecutor(client), groupManager);
    }

    @Override
    public GroupManager groupManager() {
        return groupManager;
    }

}
