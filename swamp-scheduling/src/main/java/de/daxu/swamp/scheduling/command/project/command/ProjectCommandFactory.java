package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProjectCommandFactory {

    public CreateProjectCommand createProjectCommand(ProjectId projectId, String name, String description) {
        return new CreateProjectCommand(projectId, name, description);
    }

    public ScheduleBuildCommand scheduleBuildCommand(ProjectId projectId, Set<ContainerTemplate> containerTemplates) {
        return new ScheduleBuildCommand(projectId, containerTemplates);
    }
}
