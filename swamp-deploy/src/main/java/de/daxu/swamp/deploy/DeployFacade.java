package de.daxu.swamp.deploy;

import de.daxu.swamp.deploy.configuration.Configuration;
import de.daxu.swamp.deploy.configuration.CreateContainerConfiguration;
import de.daxu.swamp.deploy.configuration.LogContainerConfiguration;
import de.daxu.swamp.deploy.response.ContainerResponse;
import de.daxu.swamp.deploy.response.CreateContainerResponse;

public interface DeployFacade {

    CreateContainerResponse createContainer( CreateContainerConfiguration config );

    ContainerResponse startContainer( Configuration configuration );

    ContainerResponse stopContainer( Configuration configuration );

    ContainerResponse removeContainer( Configuration configuration );

    ContainerResponse logContainer( LogContainerConfiguration config );
}
