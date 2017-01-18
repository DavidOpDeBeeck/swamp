package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerInstanceStoppedEvent extends ContainerInstanceDeployEvent {

    private final ContainerInstanceStopReason reason;
    private final LocalDateTime stoppedAt;

    public ContainerInstanceStoppedEvent( ContainerInstanceId containerInstanceId,
                                          Set<String> warnings,
                                          ContainerInstanceStopReason reason,
                                          LocalDateTime stoppedAt ) {
        super( containerInstanceId, warnings );
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

