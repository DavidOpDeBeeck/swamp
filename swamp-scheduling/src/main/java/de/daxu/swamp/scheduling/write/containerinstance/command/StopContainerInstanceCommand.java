package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class StopContainerInstanceCommand extends ContainerInstanceCommand {

    private final Date dateStopped;

    public StopContainerInstanceCommand( ContainerInstanceId containerInstanceId, Date dateStopped ) {
        super(containerInstanceId);
        this.dateStopped = dateStopped;
    }

    public Date getDateStopped() {
        return dateStopped;
    }
}

