package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class CreateContainerInstanceCommand extends ContainerInstanceCommand {

    public CreateContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
