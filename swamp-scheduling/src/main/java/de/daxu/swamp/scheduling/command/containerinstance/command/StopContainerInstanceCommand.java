package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;

public class StopContainerInstanceCommand extends ContainerInstanceCommand {

    private final ContainerInstanceStopReason reason;

    public StopContainerInstanceCommand( ContainerInstanceId containerInstanceId, ContainerInstanceStopReason reason ) {
        super( containerInstanceId );
        this.reason = reason;
    }

    public ContainerInstanceStopReason getReason() {
        return reason;
    }
}

