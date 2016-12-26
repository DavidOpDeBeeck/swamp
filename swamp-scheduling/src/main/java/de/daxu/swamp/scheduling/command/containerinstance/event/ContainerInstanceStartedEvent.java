package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceStartedEvent extends ContainerInstanceEvent {

    private Date dateStarted;

    public ContainerInstanceStartedEvent( ContainerInstanceId containerInstanceId, Date dateStarted ) {
        super( containerInstanceId );
        this.dateStarted = dateStarted;
    }

    public Date getDateStarted() {
        return dateStarted;
    }
}
