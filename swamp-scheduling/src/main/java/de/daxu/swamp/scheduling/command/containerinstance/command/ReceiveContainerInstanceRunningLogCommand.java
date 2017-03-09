package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ReceiveContainerInstanceRunningLogCommand
        extends AbstractReceiveContainerInstanceLogCommand {

    public ReceiveContainerInstanceRunningLogCommand(ContainerInstanceId containerInstanceId, String log) {
        super(containerInstanceId, log);
    }
}
