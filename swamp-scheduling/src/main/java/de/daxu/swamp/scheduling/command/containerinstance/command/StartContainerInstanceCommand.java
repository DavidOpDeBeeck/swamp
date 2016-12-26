package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class StartContainerInstanceCommand extends ContainerInstanceCommand {

    public StartContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
