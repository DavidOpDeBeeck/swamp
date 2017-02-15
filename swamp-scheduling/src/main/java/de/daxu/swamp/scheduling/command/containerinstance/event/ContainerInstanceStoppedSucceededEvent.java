package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

public class ContainerInstanceStoppedSucceededEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceStoppedEvent, ContainerInstanceDeploySucceededEvent {

    private final ContainerInstanceStopReason reason;

    public ContainerInstanceStoppedSucceededEvent(ContainerInstanceId containerInstanceId,
                                                  BuildId buildId,
                                                  EventMetaData eventMetaData,
                                                  ContainerId containerId,
                                                  ContainerInstanceStopReason reason) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
        this.reason = reason;
    }

    @Override
    public ContainerInstanceStopReason getReason() {
        return reason;
    }
}
