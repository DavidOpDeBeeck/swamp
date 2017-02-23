package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

public class BuildContainerInstanceStatusChangedEvent extends ContainerInstanceBuildEvent {

    private final ContainerInstanceStatus containerInstanceStatus;

    public BuildContainerInstanceStatusChangedEvent(BuildId buildId,
                                                    EventMetaData eventMetaData,
                                                    ContainerInstanceId containerInstanceId,
                                                    ContainerInstanceStatus containerInstanceStatus) {
        super(buildId, eventMetaData, containerInstanceId);
        this.containerInstanceStatus = containerInstanceStatus;
    }

    public ContainerInstanceStatus getContainerInstanceStatus() {
        return containerInstanceStatus;
    }
}
