package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.group.GroupManagerImpl;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.mapper.PortMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerFacade implements DeployFacade {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResultFactory containerResultFactory;
    private final PortMappingMapper portMappingMapper;
    private final GroupManager groupManager;

    @Autowired
    public DockerFacade( DockerClientFactory dockerClientFactory,
                         ContainerResultFactory containerResultFactory,
                         PortMappingMapper portMappingMapper ) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
        this.portMappingMapper = portMappingMapper;
        this.groupManager = new GroupManagerImpl();
    }

    @Override
    public ContainerClient containerClient( Server server ) {
        return new DockerContainerClient( dockerClientFactory, containerResultFactory, portMappingMapper, groupManager, server );
    }

    @Override
    public GroupManager groupManager() {
        return groupManager;
    }

}
