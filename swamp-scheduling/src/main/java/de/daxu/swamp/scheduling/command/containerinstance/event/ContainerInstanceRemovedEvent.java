package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceRemovedEvent extends ContainerInstanceEvent {

    private LocalDateTime removedAt;

    public ContainerInstanceRemovedEvent( ContainerInstanceId containerInstanceId, LocalDateTime removedAt ) {
        super(containerInstanceId);
        this.removedAt = removedAt;
    }

    public LocalDateTime getRemovedAt() {
        return removedAt;
    }
}

