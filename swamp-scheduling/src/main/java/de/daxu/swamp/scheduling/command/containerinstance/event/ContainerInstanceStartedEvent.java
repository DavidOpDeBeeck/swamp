package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceStartedEvent extends ContainerInstanceEvent {

    private LocalDateTime startedAt;

    public ContainerInstanceStartedEvent( ContainerInstanceId containerInstanceId,
                                          LocalDateTime startedAt ) {
        super( containerInstanceId );
        this.startedAt = startedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }
}
