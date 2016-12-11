package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class RemoveContainerInstanceCommand extends ContainerInstanceCommand {

    public RemoveContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}

