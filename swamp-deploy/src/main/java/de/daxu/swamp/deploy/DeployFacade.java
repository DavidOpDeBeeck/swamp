package de.daxu.swamp.deploy;

import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.configuration.CreateContainerConfiguration;
import de.daxu.swamp.deploy.configuration.LogContainerConfiguration;
import de.daxu.swamp.deploy.response.ContainerResponse;
import de.daxu.swamp.deploy.response.CreateContainerResponse;

public interface DeployFacade {

    CreateContainerResponse createContainer( CreateContainerConfiguration config );

    ContainerResponse startContainer( ContainerConfiguration configuration );

    ContainerResponse stopContainer( ContainerConfiguration configuration );

    ContainerResponse removeContainer( ContainerConfiguration configuration );

    ContainerResponse logContainer( LogContainerConfiguration config );
}
