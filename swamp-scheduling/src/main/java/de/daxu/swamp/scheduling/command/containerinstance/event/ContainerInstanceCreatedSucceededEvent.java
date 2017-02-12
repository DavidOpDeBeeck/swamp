package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceCreatedSucceededEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceCreatedEvent, ContainerInstanceDeploySucceededEvent {

    public ContainerInstanceCreatedSucceededEvent(ContainerInstanceId containerInstanceId,
                                                  EventMetaData eventMetaData,
                                                  ContainerId containerId) {
        super(containerInstanceId, eventMetaData, containerId);
    }
}
