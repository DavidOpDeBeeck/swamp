package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class StartContainerInstanceCommand extends ContainerInstanceCommand {

    private final Date dateStarted;

    public StartContainerInstanceCommand( ContainerInstanceId containerInstanceId, Date dateStarted ) {
        super(containerInstanceId);
        this.dateStarted = dateStarted;
    }

    public Date getDateStarted() {
        return dateStarted;
    }
}
