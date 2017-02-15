package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceDeployActionFailedEvent extends AbstractContainerInstanceEvent {

    private final Set<String> warnings;

    public ContainerInstanceDeployActionFailedEvent( ContainerInstanceId containerInstanceId,
                                                     EventMetaData eventMetaData,
                                                     BuildId buildId,
                                                     Set<String> warnings ) {
        super( containerInstanceId, buildId, eventMetaData);
        this.warnings = warnings;
    }

    public Set<String> getWarnings() {
        return warnings;
    }
}
