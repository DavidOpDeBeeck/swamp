package de.daxu.swamp.deploy;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.group.GroupManager;

public interface DeployFacade {

    ContainerClient containerClient( Server server );

    GroupManager groupManager();

}
