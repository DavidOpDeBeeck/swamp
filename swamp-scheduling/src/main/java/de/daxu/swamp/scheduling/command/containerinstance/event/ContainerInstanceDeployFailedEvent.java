package de.daxu.swamp.scheduling.command.containerinstance.event;

import java.util.Set;

public interface ContainerInstanceDeployFailedEvent {

    Set<String> getErrors();
}
