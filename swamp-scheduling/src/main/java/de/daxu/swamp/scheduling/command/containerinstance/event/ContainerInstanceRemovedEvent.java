package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

public interface ContainerInstanceRemovedEvent extends ContainerInstanceDeployEvent {

    ContainerInstanceRemoveReason getReason();
}

