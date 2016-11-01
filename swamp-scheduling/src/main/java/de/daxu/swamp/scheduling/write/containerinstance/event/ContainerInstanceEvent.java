package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.scheduling.write.Event;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceEvent implements Event {

    private ContainerInstanceId containerInstanceId;

    ContainerInstanceEvent() {
    }

    ContainerInstanceEvent( ContainerInstanceId containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
