package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class StartContainerInstanceLoggingCommand extends ContainerInstanceCommand {

    public StartContainerInstanceLoggingCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
