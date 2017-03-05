package de.daxu.swamp.scheduling.command.containerinstance.event;

public interface ContainerInstanceLogReceivedEvent
    extends ContainerInstanceDeploySucceededEvent {

    String getLog();
}
