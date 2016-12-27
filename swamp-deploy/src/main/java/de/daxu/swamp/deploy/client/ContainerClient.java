package de.daxu.swamp.deploy.client;

import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.CreateContainerResult;

import java.util.function.Consumer;

public interface ContainerClient extends DeployClient {

    CreateContainerResult create( ContainerConfiguration config );

    ContainerResult start( ContainerId containerId );

    ContainerResult stop( ContainerId containerId );

    ContainerResult remove( ContainerId containerId );

    ContainerResult log( ContainerId containerId, Consumer<String> logCallback );
}
