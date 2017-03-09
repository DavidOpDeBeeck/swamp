package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public abstract class AbstractReceiveContainerInstanceLogCommand extends ContainerInstanceCommand {

    private final String log;

    public AbstractReceiveContainerInstanceLogCommand(ContainerInstanceId containerInstanceId, String log ) {
        super( containerInstanceId );
        this.log = log;
    }

    public String getLog() {
        return log;
    }
}
