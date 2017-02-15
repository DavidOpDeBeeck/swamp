package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

public class ContainerInstanceRemovedSucceededEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceRemovedEvent, ContainerInstanceDeploySucceededEvent {

    private final ContainerInstanceRemoveReason reason;

    public ContainerInstanceRemovedSucceededEvent(ContainerInstanceId containerInstanceId,
                                                  BuildId buildId,
                                                  EventMetaData eventMetaData,
                                                  ContainerId containerId,
                                                  ContainerInstanceRemoveReason reason) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
        this.reason = reason;
    }

    @Override
    public ContainerInstanceRemoveReason getReason() {
        return reason;
    }
}
