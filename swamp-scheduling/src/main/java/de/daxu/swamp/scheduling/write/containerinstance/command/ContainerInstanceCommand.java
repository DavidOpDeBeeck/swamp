package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceCommand {

    private ContainerInstanceId containerInstanceId;

    ContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
