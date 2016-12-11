package de.daxu.swamp.deploy.client;

import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;
import de.daxu.swamp.deploy.response.CreateContainerResponse;

import java.util.function.Consumer;

public interface ContainerClient extends DeployClient {

    CreateContainerResponse create( ContainerConfiguration config );

    ContainerResponse start( ContainerId containerId );

    ContainerResponse stop( ContainerId containerId );

    ContainerResponse remove( ContainerId containerId );

    ContainerResponse log( ContainerId containerId, Consumer<String> logCallback );
}
