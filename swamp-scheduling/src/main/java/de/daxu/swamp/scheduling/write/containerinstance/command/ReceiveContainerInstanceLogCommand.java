package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class ReceiveContainerInstanceLogCommand extends ContainerInstanceCommand {

    private final String log;

    public ReceiveContainerInstanceLogCommand( ContainerInstanceId containerInstanceId, String log ) {
        super( containerInstanceId );
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
