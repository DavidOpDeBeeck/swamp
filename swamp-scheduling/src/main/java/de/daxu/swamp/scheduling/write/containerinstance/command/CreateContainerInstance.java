package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

public class CreateContainerInstance extends ContainerInstanceCommand {

    private String name;

    public CreateContainerInstance( ContainerInstanceId containerInstanceId, String name ) {
        super(containerInstanceId);
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
