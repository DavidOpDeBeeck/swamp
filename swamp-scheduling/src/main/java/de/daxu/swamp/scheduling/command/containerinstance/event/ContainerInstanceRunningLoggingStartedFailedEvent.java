package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class ContainerInstanceRunningLoggingStartedFailedEvent
        extends AbstractContainerInstanceLoggingStartedFailedEvent
        implements ContainerInstanceRunningLoggingStartedEvent {

    public ContainerInstanceRunningLoggingStartedFailedEvent(ContainerInstanceId containerInstanceId,
                                                             BuildId buildId,
                                                             EventMetaData eventMetaData,
                                                             ContainerId containerId,
                                                             Set<String> errors) {
        super(containerInstanceId, buildId, eventMetaData, containerId, errors);
    }
}
