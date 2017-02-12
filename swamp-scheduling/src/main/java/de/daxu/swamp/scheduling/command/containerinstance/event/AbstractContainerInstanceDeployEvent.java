package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public abstract class AbstractContainerInstanceDeployEvent
        extends AbstractContainerInstanceEvent implements ContainerInstanceDeployEvent {

    private final ContainerId containerId;

    public AbstractContainerInstanceDeployEvent(ContainerInstanceId containerInstanceId,
                                                EventMetaData eventMetaData,
                                                ContainerId containerId) {
        super(containerInstanceId, eventMetaData);
        this.containerId = containerId;
    }

    @Override
    public ContainerId getContainerId() {
        return containerId;
    }

}
