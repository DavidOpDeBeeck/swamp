package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceEvent;

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
