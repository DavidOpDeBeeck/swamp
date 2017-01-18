package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerInstanceRemovedEvent extends ContainerInstanceDeployEvent {

    private final ContainerInstanceRemoveReason reason;
    private final LocalDateTime removedAt;

    public ContainerInstanceRemovedEvent( ContainerInstanceId containerInstanceId,
                                          Set<String> warnings,
                                          ContainerInstanceRemoveReason reason,
                                          LocalDateTime removedAt ) {
        super( containerInstanceId, warnings );
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

