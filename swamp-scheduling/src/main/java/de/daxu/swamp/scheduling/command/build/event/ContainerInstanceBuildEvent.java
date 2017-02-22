package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceBuildEvent extends BuildEvent {

    private final ContainerInstanceId containerInstanceId;

    public ContainerInstanceBuildEvent(BuildId buildId,
                                       EventMetaData eventMetaData,
                                       ContainerInstanceId containerInstanceId) {
        super(buildId, eventMetaData);
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
