package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

public interface ContainerInstanceStatusChangedEvent extends ContainerInstanceEvent {

    ContainerInstanceStatus getContainerInstanceStatus();

}
