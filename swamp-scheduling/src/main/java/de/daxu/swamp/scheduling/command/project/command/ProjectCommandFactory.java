package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProjectCommandFactory {

    public CreateProjectCommand createProjectCommand(ProjectId projectId, String name, String description) {
        return new CreateProjectCommand(projectId, name, description);
    }

    public ScheduleBuildCommand scheduleBuildCommand(ProjectId projectId, Set<Container> containers) {
        return new ScheduleBuildCommand(projectId, containers);
    }

    public AddBuildCommand addBuildCommand(ProjectId projectId, BuildId buildId) {
        return new AddBuildCommand(projectId, buildId);
    }
}
