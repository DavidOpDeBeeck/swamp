package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceStartedFailedEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceStartedEvent, ContainerInstanceDeployFailedEvent {

    private final Set<String> errors;

    public ContainerInstanceStartedFailedEvent(ContainerInstanceId containerInstanceId,
                                               EventMetaData eventMetaData,
                                               ContainerId containerId,
                                               Set<String> errors) {
        super(containerInstanceId, eventMetaData, containerId);
        this.errors = errors;
    }

    @Override
    public Set<String> getErrors() {
        return errors;
    }
}
