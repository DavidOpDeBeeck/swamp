package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public abstract class AbstractContainerInstanceDeployEvent
        extends AbstractContainerInstanceEvent implements ContainerInstanceDeployEvent {

    private final ContainerId containerId;

    public AbstractContainerInstanceDeployEvent(ContainerInstanceId containerInstanceId,
                                                BuildId buildId,
                                                EventMetaData eventMetaData,
                                                ContainerId containerId) {
        super(containerInstanceId, buildId, eventMetaData);
        this.containerId = containerId;
    }

    @Override
    public ContainerId getContainerId() {
        return containerId;
    }

}
