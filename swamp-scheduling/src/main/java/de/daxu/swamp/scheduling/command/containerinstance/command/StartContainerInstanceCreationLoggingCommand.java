package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class StartContainerInstanceCreationLoggingCommand extends ContainerInstanceCommand {

    public StartContainerInstanceCreationLoggingCommand(ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
