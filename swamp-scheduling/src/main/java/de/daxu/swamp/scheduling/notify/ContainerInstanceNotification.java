package de.daxu.swamp.scheduling.notify;

import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceEvent;

public class ContainerInstanceNotification {

    private String type;
    private ContainerInstanceEvent event;

    public ContainerInstanceNotification( ContainerInstanceEvent event ) {
        this.type = event.getClass().getSimpleName();
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public ContainerInstanceEvent getEvent() {
        return event;
    }
}
