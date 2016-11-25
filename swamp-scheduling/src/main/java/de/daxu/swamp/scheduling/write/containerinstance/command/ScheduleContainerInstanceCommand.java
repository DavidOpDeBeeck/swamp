package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ScheduleContainerInstanceCommand extends ContainerInstanceCommand {

    private final Date dateScheduled;
    private final Container container;
    private final Server server;

    public ScheduleContainerInstanceCommand( ContainerInstanceId containerInstanceId, Container container, Server server, Date dateScheduled ) {
        super( containerInstanceId );
        this.container = container;
        this.dateScheduled = dateScheduled;
        this.server = server;
    }

    public Container getContainer() {
        return container;
    }

    public Server getServer() {
        return server;
    }

    public Date getDateScheduled() {
        return dateScheduled;
    }
}
