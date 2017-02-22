package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

public class ContainerInstanceStatusUpdatedFromBuildEvent extends ContainerInstanceBuildEvent {

    private final ContainerInstanceStatus status;

    public ContainerInstanceStatusUpdatedFromBuildEvent(BuildId buildId,
                                                        EventMetaData eventMetaData,
                                                        ContainerInstanceId containerInstanceId,
                                                        ContainerInstanceStatus status) {
        super(buildId, eventMetaData, containerInstanceId);
        this.status = status;
    }

    public ContainerInstanceStatus getStatus() {
        return status;
    }
}
