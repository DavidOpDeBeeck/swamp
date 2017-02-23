package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

public interface ContainerInstanceStoppedEvent
        extends ContainerInstanceDeployEvent, ContainerInstanceStatusChangedEvent {

    ContainerInstanceStopReason getReason();

    @Override
    default ContainerInstanceStatus getContainerInstanceStatus(){
        return ContainerInstanceStatus.STOPPED;
    }
}

