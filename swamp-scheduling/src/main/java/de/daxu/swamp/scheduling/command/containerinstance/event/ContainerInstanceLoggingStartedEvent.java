package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceLoggingStartedEvent extends ContainerInstanceEvent {

    private final LocalDateTime dateLoggingStarted;

    public ContainerInstanceLoggingStartedEvent( ContainerInstanceId containerInstanceId,
                                                 LocalDateTime dateLoggingStarted ) {
        super( containerInstanceId );
        this.dateLoggingStarted = dateLoggingStarted;
    }

    public LocalDateTime getDateLoggingStarted() {
        return dateLoggingStarted;
    }
}
