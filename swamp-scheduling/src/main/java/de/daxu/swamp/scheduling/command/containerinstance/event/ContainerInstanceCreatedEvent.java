package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceCreatedEvent extends ContainerInstanceEvent {

    private final String internalContainerId;
    private final ContainerId containerId;
    private final Date dateCreated;

    public ContainerInstanceCreatedEvent( ContainerInstanceId containerInstanceId,
                                          ContainerId containerId,
                                          String internalContainerId,
                                          Date dateCreated ) {
        super( containerInstanceId );
        this.containerId = containerId;
        this.internalContainerId = internalContainerId;
        this.dateCreated = dateCreated;
    }

    public ContainerId getContainerId() {
        return containerId;
    }

    public String getInternalContainerId() {
        return internalContainerId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
