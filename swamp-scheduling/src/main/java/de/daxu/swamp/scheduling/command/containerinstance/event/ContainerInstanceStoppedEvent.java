package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceStoppedEvent extends ContainerInstanceEvent {

    private LocalDateTime stoppedAt;

    public ContainerInstanceStoppedEvent( ContainerInstanceId containerInstanceId, LocalDateTime stoppedAt ) {
        super(containerInstanceId);
        this.stoppedAt = stoppedAt;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }
}

