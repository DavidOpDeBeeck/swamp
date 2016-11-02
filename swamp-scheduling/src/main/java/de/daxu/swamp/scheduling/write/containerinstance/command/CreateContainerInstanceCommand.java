package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.common.cqrs.AbstractCommand;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class CreateContainerInstanceCommand extends AbstractCommand<ContainerInstanceId> {

    private String name;

    public CreateContainerInstanceCommand( ContainerInstanceId containerInstanceId, String name ) {
        super(containerInstanceId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
