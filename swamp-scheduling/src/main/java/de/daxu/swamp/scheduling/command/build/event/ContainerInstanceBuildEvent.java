package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class ContainerInstanceBuildEvent extends BuildEvent {

    private final ContainerInstanceId containerInstanceId;

    public ContainerInstanceBuildEvent(BuildId buildId,
                                       ProjectId projectId,
                                       EventMetaData eventMetaData,
                                       ContainerInstanceId containerInstanceId) {
        super(buildId, projectId, eventMetaData);
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
