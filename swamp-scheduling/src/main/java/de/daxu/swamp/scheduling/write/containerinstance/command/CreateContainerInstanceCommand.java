package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class CreateContainerInstanceCommand extends ContainerInstanceCommand {

    private final String internalContainerId;
    private final String internalContainerName;
    private final Date dateCreated;
    private final Server server;

    public CreateContainerInstanceCommand( ContainerInstanceId containerInstanceId, String internalContainerId, String internalContainerName, Date dateCreated, Server server ) {
        super( containerInstanceId );
        this.internalContainerId = internalContainerId;
        this.internalContainerName = internalContainerName;
        this.dateCreated = dateCreated;
        this.server = server;
    }

    public String getInternalContainerId() {
        return internalContainerId;
    }

    public String getInternalContainerName() {
        return internalContainerName;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Server getServer() {
        return server;
    }
}
