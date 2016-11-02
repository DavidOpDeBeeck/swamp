package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.common.cqrs.AbstractEvent;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceCreated extends AbstractEvent<ContainerInstanceId> {

    private String name;

    public ContainerInstanceCreated( ContainerInstanceId containerInstanceId, String name ) {
        super(containerInstanceId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
