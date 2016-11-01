package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceCreated extends ContainerInstanceEvent {

    private String name;

    public ContainerInstanceCreated() {
    }

    public ContainerInstanceCreated( ContainerInstanceId containerInstanceId, String name ) {
        super(containerInstanceId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
