package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ReceiveContainerInstanceCreationLogCommand
        extends AbstractReceiveContainerInstanceLogCommand {

    public ReceiveContainerInstanceCreationLogCommand(ContainerInstanceId containerInstanceId, String log) {
        super(containerInstanceId, log);
    }
}
