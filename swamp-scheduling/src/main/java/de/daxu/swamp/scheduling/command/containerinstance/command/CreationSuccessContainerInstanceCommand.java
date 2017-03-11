package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class CreationSuccessContainerInstanceCommand extends ContainerInstanceCommand {

    private final ContainerId containerId;

    public CreationSuccessContainerInstanceCommand(ContainerInstanceId containerInstanceId, ContainerId containerId) {
        super( containerInstanceId );
        this.containerId = containerId;
    }

    public ContainerId getContainerId() {
        return containerId;
    }
}
