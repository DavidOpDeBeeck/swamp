package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AbstractContainerInstanceLoggingStartedSucceededEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceLoggingStartedEvent, ContainerInstanceDeploySucceededEvent {

    public AbstractContainerInstanceLoggingStartedSucceededEvent(ContainerInstanceId containerInstanceId,
                                                                 BuildId buildId,
                                                                 EventMetaData eventMetaData,
                                                                 ContainerId containerId) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
    }
}
