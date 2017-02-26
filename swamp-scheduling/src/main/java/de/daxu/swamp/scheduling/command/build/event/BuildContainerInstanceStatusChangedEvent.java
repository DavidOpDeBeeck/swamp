package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class BuildContainerInstanceStatusChangedEvent extends ContainerInstanceBuildEvent {

    private final ContainerInstanceStatus containerInstanceStatus;

    public BuildContainerInstanceStatusChangedEvent(BuildId buildId,
                                                    ProjectId projectId,
                                                    EventMetaData eventMetaData,
                                                    ContainerInstanceId containerInstanceId,
                                                    ContainerInstanceStatus containerInstanceStatus) {
        super(buildId, projectId, eventMetaData, containerInstanceId);
        this.containerInstanceStatus = containerInstanceStatus;
    }

    public ContainerInstanceStatus getContainerInstanceStatus() {
        return containerInstanceStatus;
    }
}
