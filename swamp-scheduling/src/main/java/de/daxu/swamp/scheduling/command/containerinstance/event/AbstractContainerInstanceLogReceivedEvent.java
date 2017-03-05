package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AbstractContainerInstanceLogReceivedEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceLogReceivedEvent {

    private final String log;

    public AbstractContainerInstanceLogReceivedEvent(ContainerInstanceId containerInstanceId,
                                                     BuildId buildId,
                                                     EventMetaData eventMetaData,
                                                     ContainerId containerId,
                                                     String log) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
        this.log = log;
    }

    @Override
    public String getLog() {
        return log;
    }
}
