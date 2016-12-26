package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ContainerInstanceCommand {

    @TargetAggregateIdentifier
    private ContainerInstanceId containerInstanceId;

    public ContainerInstanceCommand( ContainerInstanceId containerInstanceId ) {
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}
