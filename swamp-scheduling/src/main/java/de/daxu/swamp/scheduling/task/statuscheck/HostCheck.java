package de.daxu.swamp.scheduling.task.statuscheck;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;

public abstract class HostCheck implements StatusChangeCheck {

    private final ContainerClient containerClient;

    public HostCheck( Server server, DeployFacade deployFacade ) {
        this.containerClient = deployFacade.containerClient( server );
    }

    @Override
    public abstract boolean check( ContainerInstanceView containerInstanceView );

    public ContainerClient getContainerClient() {
        return containerClient;
    }
}
