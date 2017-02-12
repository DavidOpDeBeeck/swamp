package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceLoggingStartedSucceededEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceLoggingStartedEvent, ContainerInstanceDeploySucceededEvent {

    public ContainerInstanceLoggingStartedSucceededEvent(ContainerInstanceId containerInstanceId,
                                                         EventMetaData eventMetaData,
                                                         ContainerId containerId) {
        super(containerInstanceId, eventMetaData, containerId);
    }
}
