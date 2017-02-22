package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AddContainerInstanceToBuildCommand extends ContainerInstanceBuildCommand {

    public AddContainerInstanceToBuildCommand(BuildId buildId, ContainerInstanceId containerInstanceId) {
        super(buildId, containerInstanceId);
    }
}
