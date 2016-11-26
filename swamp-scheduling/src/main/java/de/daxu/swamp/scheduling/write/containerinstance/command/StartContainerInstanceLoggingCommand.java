package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class StartContainerInstanceLoggingCommand extends ContainerInstanceCommand {

    private final Date dateLoggingStarted;

    public StartContainerInstanceLoggingCommand( ContainerInstanceId containerInstanceId, Date dateLoggingStarted ) {
        super( containerInstanceId );
        this.dateLoggingStarted = dateLoggingStarted;
    }

    public Date getDateLoggingStarted() {
        return dateLoggingStarted;
    }
}
