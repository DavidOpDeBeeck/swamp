package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceBuildCommand extends BuildCommand {

    private final ContainerInstanceId containerInstanceId;

    public ContainerInstanceBuildCommand(BuildId buildId,
                                         ContainerInstanceId containerInstanceId) {
        super(buildId);
        this.containerInstanceId = containerInstanceId;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }
}

