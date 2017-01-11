package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;

public class RemoveContainerInstanceCommand extends ContainerInstanceCommand {

    private final ContainerInstanceRemoveReason reason;

    public RemoveContainerInstanceCommand( ContainerInstanceId containerInstanceId, ContainerInstanceRemoveReason reason ) {
        super( containerInstanceId );
        this.reason = reason;
    }

    public ContainerInstanceRemoveReason getReason() {
        return reason;
    }
}

