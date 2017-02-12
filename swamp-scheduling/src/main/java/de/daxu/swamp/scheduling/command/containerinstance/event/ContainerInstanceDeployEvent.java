package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.deploy.container.ContainerId;

public interface ContainerInstanceDeployEvent extends ContainerInstanceEvent {

    ContainerId getContainerId();

}
