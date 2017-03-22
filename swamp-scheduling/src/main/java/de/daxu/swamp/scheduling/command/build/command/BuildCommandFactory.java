package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BuildCommandFactory {

    public CreateBuildCommand createBuildCommand(BuildId buildId,
                                                 ProjectId projectId,
                                                 int sequence,
                                                 Set<ContainerTemplate> containerTemplates) {
        return new CreateBuildCommand(buildId, projectId, sequence, containerTemplates);
    }

    public UpdateContainerInstanceStatusCommand updateContainerInstanceStatusCommand(BuildId buildId,
                                                                                     ContainerInstanceId containerInstanceId,
                                                                                     ContainerInstanceStatus status) {
        return new UpdateContainerInstanceStatusCommand(buildId, containerInstanceId, status);
    }
}
