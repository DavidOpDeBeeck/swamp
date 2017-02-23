package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class BuildContainerInstanceAddedEvent extends ContainerInstanceBuildEvent {

    public BuildContainerInstanceAddedEvent(BuildId buildId,
                                            EventMetaData eventMetaData,
                                            ContainerInstanceId containerInstanceId) {
        super(buildId, eventMetaData, containerInstanceId);
    }
}
