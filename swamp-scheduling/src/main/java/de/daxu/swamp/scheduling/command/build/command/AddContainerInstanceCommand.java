package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AddContainerInstanceCommand extends ContainerInstanceBuildCommand {

    public AddContainerInstanceCommand(BuildId buildId, ContainerInstanceId containerInstanceId) {
        super(buildId, containerInstanceId);
    }
}
