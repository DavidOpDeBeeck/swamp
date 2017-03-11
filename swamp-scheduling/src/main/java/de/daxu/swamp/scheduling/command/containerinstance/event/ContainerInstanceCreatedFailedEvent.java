package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceCreatedFailedEvent
        extends AbstractContainerInstanceEvent
        implements ContainerInstanceCreatedEvent, ContainerInstanceDeployFailedEvent {

    private final Set<String> errors;

    public ContainerInstanceCreatedFailedEvent(ContainerInstanceId containerInstanceId,
                                               BuildId buildId,
                                               EventMetaData eventMetaData,
                                               Set<String> errors) {
        super(containerInstanceId, buildId, eventMetaData);
        this.errors = errors;
    }

    @Override
    public Set<String> getErrors() {
        return errors;
    }
}
