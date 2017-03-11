package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceCreationStartedEvent
        extends AbstractContainerInstanceEvent {

    public ContainerInstanceCreationStartedEvent(ContainerInstanceId containerInstanceId,
                                                 BuildId buildId,
                                                 EventMetaData eventMetaData) {
        super(containerInstanceId, buildId, eventMetaData);
    }
}
