package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

import java.util.Set;

public class ContainerInstanceRemovedFailedEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceRemovedEvent, ContainerInstanceDeployFailedEvent {

    private final Set<String> errors;
    private final ContainerInstanceRemoveReason reason;

    public ContainerInstanceRemovedFailedEvent(ContainerInstanceId containerInstanceId,
                                               EventMetaData eventMetaData,
                                               ContainerId containerId,
                                               Set<String> errors,
                                               ContainerInstanceRemoveReason reason) {
        super(containerInstanceId, eventMetaData, containerId);
        this.errors = errors;
        this.reason = reason;
    }

    @Override
    public Set<String> getErrors() {
        return errors;
    }

    @Override
    public ContainerInstanceRemoveReason getReason() {
        return reason;
    }
}
