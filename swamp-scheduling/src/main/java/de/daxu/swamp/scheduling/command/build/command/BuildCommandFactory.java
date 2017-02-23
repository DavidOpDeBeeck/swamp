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

    public AddContainerInstanceCommand createAddContainerInstanceCommand(BuildId buildId,
                                                                         ContainerInstanceId containerInstanceId) {
        return new AddContainerInstanceCommand(buildId, containerInstanceId);
    }

    public UpdateContainerInstanceStatusCommand createUpdateContainerInstanceStatusCommand(BuildId buildId,
                                                                                           ContainerInstanceId containerInstanceId,
                                                                                           ContainerInstanceStatus status) {
        return new UpdateContainerInstanceStatusCommand(buildId, containerInstanceId, status);
    }
}
