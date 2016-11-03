package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceCreatedEvent extends ContainerInstanceEvent {

    private String name;

    public ContainerInstanceCreatedEvent( ContainerInstanceId containerInstanceId, String name ) {
        super(containerInstanceId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
