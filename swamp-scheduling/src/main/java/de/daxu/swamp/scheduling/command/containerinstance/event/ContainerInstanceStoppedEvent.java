package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

import java.time.LocalDateTime;

public class ContainerInstanceStoppedEvent extends ContainerInstanceEvent {

    private final ContainerInstanceStopReason reason;
    private final LocalDateTime stoppedAt;

    public ContainerInstanceStoppedEvent( ContainerInstanceId containerInstanceId,
                                          ContainerInstanceStopReason reason,
                                          LocalDateTime stoppedAt ) {
        super(containerInstanceId);
        this.reason = reason;
        this.stoppedAt = stoppedAt;
    }

    public ContainerInstanceStopReason getReason() {
        return reason;
    }

    public LocalDateTime getStoppedAt() {
        return stoppedAt;
    }
}

