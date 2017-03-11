package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class StartContainerInstanceRunningLoggingCommand extends ContainerInstanceCommand {

    public StartContainerInstanceRunningLoggingCommand(ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
