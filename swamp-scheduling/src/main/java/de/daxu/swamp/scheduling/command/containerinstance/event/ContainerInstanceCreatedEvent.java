package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

public interface ContainerInstanceCreatedEvent
        extends ContainerInstanceDeployEvent, ContainerInstanceStatusChangedEvent {

    @Override
    default ContainerInstanceStatus getContainerInstanceStatus(){
        return ContainerInstanceStatus.CREATED;
    }
}
