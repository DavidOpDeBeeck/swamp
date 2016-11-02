package de.daxu.swamp.scheduling.notify;

import de.daxu.swamp.common.cqrs.AbstractEvent;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ContainerInstanceNotification {

    private String type;
    private AbstractEvent<ContainerInstanceId> event;

    public ContainerInstanceNotification( AbstractEvent<ContainerInstanceId> event ) {
        this.type = event.getClass().getSimpleName();
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public AbstractEvent<ContainerInstanceId> getEvent() {
        return event;
    }
}
