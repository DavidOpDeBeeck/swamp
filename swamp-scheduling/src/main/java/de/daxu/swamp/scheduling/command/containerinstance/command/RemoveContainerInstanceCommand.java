package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class RemoveContainerInstanceCommand extends ContainerInstanceCommand {

    public RemoveContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}

