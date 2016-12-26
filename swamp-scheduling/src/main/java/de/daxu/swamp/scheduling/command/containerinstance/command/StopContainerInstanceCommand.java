package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class StopContainerInstanceCommand extends ContainerInstanceCommand {

    public StopContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}

