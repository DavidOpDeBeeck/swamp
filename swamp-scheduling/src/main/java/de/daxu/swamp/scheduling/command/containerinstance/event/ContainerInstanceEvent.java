package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceEvent {

    private ContainerInstanceId containerInstanceId;

    public ContainerInstanceEvent( ContainerInstanceId containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
