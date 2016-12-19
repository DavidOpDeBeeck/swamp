package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.response.ContainerResponseFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.mapper.PortMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DockerFacade implements DeployFacade {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResponseFactory containerResponseFactory;
    private final PortMappingMapper portMappingMapper;

    @Autowired
    public DockerFacade( DockerClientFactory dockerClientFactory,
                         ContainerResponseFactory containerResponseFactory,
                         PortMappingMapper portMappingMapper ) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResponseFactory = containerResponseFactory;
        this.portMappingMapper = portMappingMapper;
    }

    @Override
    public ContainerClient containerClient( Server server ) {
        return new DockerContainerClient( dockerClientFactory, containerResponseFactory, portMappingMapper, server );
    }
}
