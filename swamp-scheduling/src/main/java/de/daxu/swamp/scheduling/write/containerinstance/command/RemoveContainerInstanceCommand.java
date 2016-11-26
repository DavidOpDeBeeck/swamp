package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class RemoveContainerInstanceCommand extends ContainerInstanceCommand {

    private final Date dateRemoved;

    public RemoveContainerInstanceCommand( ContainerInstanceId containerInstanceId, Date dateRemoved ) {
        super(containerInstanceId);
        this.dateRemoved = dateRemoved;
    }

    public Date getDateRemoved() {
        return dateRemoved;
    }
}

