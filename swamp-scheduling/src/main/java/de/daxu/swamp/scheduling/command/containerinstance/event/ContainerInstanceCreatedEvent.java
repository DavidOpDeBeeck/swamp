package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerInstanceCreatedEvent extends ContainerInstanceDeployEvent {

    private final ContainerId containerId;
    private final LocalDateTime createdAt;

    public ContainerInstanceCreatedEvent( ContainerInstanceId containerInstanceId,
                                          Set<String> warnings,
                                          ContainerId containerId,
                                          LocalDateTime createdAt ) {
        super( containerInstanceId, warnings );
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
