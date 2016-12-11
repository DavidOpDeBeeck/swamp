package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class StartContainerInstanceLoggingCommand extends ContainerInstanceCommand {

    public StartContainerInstanceLoggingCommand( ContainerInstanceId containerInstanceId ) {
        super( containerInstanceId );
    }
}
