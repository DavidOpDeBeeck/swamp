package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.Event;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public interface ContainerInstanceEvent extends Event {

    ContainerInstanceId getContainerInstanceId();

    BuildId getBuildId();
}
