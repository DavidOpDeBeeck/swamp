package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BuildCommandFactory {

    public InitializeBuildCommand createInitializeCommand(BuildId buildId,
                                                          Project project,
                                                          Set<ContainerInstanceId> containerInstanceIds,
                                                          int sequence) {
        return new InitializeBuildCommand(buildId, sequence, project.getName(), project.getDescription(), containerInstanceIds);
    }

    public AddContainerInstanceToBuildCommand createAddContainerInstanceToBuildCommand(BuildId buildId,
                                                                                       ContainerInstanceId containerInstanceId) {
        return new AddContainerInstanceToBuildCommand(buildId, containerInstanceId);
    }

    public UpdateContainerInstanceStatusFromBuildCommand createUpdateContainerInstanceStatusFromBuildCommand(BuildId buildId,
                                                                                                             ContainerInstanceId containerInstanceId,
                                                                                                             ContainerInstanceStatus status) {
        return new UpdateContainerInstanceStatusFromBuildCommand(buildId, containerInstanceId, status);
    }
}
