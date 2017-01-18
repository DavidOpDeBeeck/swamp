package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;
import java.util.Set;

public class ContainerInstanceStartedEvent extends ContainerInstanceDeployEvent {

    private LocalDateTime startedAt;

    public ContainerInstanceStartedEvent( ContainerInstanceId containerInstanceId,
                                          Set<String> warnings,
                                          LocalDateTime startedAt ) {
        super( containerInstanceId, warnings );
        this.startedAt = startedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }
}
