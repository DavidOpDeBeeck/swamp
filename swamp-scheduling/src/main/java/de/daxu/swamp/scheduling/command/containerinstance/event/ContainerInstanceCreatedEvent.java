package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceCreatedEvent extends ContainerInstanceEvent {

    private final ContainerId containerId;
    private final LocalDateTime createdAt;

    public ContainerInstanceCreatedEvent( ContainerInstanceId containerInstanceId,
                                          ContainerId containerId,
                                          LocalDateTime createdAt ) {
        super( containerInstanceId );
        this.containerId = containerId;
        this.createdAt = createdAt;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
