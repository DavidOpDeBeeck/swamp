package de.daxu.swamp.deploy;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;

public interface DeployFacade {

    ContainerClient containerClient( Server server );

}
