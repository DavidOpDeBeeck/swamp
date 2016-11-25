package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class CreateContainerInstanceCommand extends ContainerInstanceCommand {

    private final String internalContainerId;
    private final String internalContainerName;
    private final Date dateCreated;

    public CreateContainerInstanceCommand( ContainerInstanceId containerInstanceId, String internalContainerId, String internalContainerName, Date dateCreated ) {
        super( containerInstanceId );
        this.internalContainerId = internalContainerId;
        this.internalContainerName = internalContainerName;
        this.dateCreated = dateCreated;
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
}
