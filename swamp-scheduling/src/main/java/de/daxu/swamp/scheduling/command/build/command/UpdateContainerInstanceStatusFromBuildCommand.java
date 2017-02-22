package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;

public class UpdateContainerInstanceStatusFromBuildCommand extends ContainerInstanceBuildCommand {

    private final ContainerInstanceStatus status;

    public UpdateContainerInstanceStatusFromBuildCommand(BuildId buildId,
                                                         ContainerInstanceId containerInstanceId,
                                                         ContainerInstanceStatus status) {
        super(buildId, containerInstanceId);
        this.status = status;
    }

    public ContainerInstanceStatus getContainerInstanceStatus() {
        return status;
    }
}
