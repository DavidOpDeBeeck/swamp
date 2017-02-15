package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceLogReceivedEvent
        extends AbstractContainerInstanceDeployEvent
        implements ContainerInstanceDeploySucceededEvent {

    private final String log;

    public ContainerInstanceLogReceivedEvent(ContainerInstanceId containerInstanceId,
                                             BuildId buildId,
                                             EventMetaData eventMetaData,
                                             ContainerId containerId,
                                             String log) {
        super(containerInstanceId, buildId, eventMetaData, containerId);
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
