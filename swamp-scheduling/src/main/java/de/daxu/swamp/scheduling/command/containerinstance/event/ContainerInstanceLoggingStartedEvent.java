package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceLoggingStartedEvent extends ContainerInstanceEvent {

    private final Date dateLoggingStarted;

    public ContainerInstanceLoggingStartedEvent( ContainerInstanceId containerInstanceId, Date dateLoggingStarted ) {
        super( containerInstanceId );
        this.dateLoggingStarted = dateLoggingStarted;
    }

    public Date getDateLoggingStarted() {
        return dateLoggingStarted;
    }
}
