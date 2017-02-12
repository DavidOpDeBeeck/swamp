package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

public interface ContainerInstanceStoppedEvent extends ContainerInstanceDeployEvent {

    ContainerInstanceStopReason getReason();
}

