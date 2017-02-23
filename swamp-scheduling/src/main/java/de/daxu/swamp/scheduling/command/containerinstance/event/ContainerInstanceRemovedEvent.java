package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

public interface ContainerInstanceRemovedEvent
        extends ContainerInstanceDeployEvent, ContainerInstanceStatusChangedEvent {

    ContainerInstanceRemoveReason getReason();

    @Override
    default ContainerInstanceStatus getContainerInstanceStatus(){
        return ContainerInstanceStatus.REMOVED;
    }
}

