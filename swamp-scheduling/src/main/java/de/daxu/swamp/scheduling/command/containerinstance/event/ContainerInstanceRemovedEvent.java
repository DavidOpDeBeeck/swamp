package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceRemovedEvent extends ContainerInstanceEvent {

    private Date dateRemoved;

    public ContainerInstanceRemovedEvent( ContainerInstanceId containerInstanceId, Date dateRemoved ) {
        super(containerInstanceId);
        this.dateRemoved = dateRemoved;
    }

    public Date getDateRemoved() {
        return dateRemoved;
    }
}

