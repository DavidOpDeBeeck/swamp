package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerInstanceLoggingStartedEvent extends ContainerInstanceDeployEvent {

    private final LocalDateTime dateLoggingStarted;

    public ContainerInstanceLoggingStartedEvent( ContainerInstanceId containerInstanceId,
                                                 Set<String> warnings,
                                                 LocalDateTime dateLoggingStarted ) {
        super( containerInstanceId, warnings );
        this.dateLoggingStarted = dateLoggingStarted;
    }

    public LocalDateTime getDateLoggingStarted() {
        return dateLoggingStarted;
    }
}
