package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

import java.time.LocalDateTime;

public class ContainerInstanceRemovedEvent extends ContainerInstanceEvent {

    private final ContainerInstanceRemoveReason reason;
    private final LocalDateTime removedAt;

    public ContainerInstanceRemovedEvent( ContainerInstanceId containerInstanceId,
                                          ContainerInstanceRemoveReason reason,
                                          LocalDateTime removedAt ) {
        super( containerInstanceId );
        this.removedAt = removedAt;
        this.reason = reason;
    }

    public ContainerInstanceRemoveReason getReason() {
        return reason;
    }

    public LocalDateTime getRemovedAt() {
        return removedAt;
    }
}

