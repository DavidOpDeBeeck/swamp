package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceStoppedEvent extends ContainerInstanceEvent {

    private Date dateStopped;

    public ContainerInstanceStoppedEvent( ContainerInstanceId containerInstanceId, Date dateStopped ) {
        super(containerInstanceId);
        this.dateStopped = dateStopped;
    }

    public Date getDateStopped() {
        return dateStopped;
    }
}

