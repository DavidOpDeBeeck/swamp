package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ProjectCommand {

    @TargetAggregateIdentifier
    private final ProjectId projectId;

    public ProjectCommand(ProjectId projectId) {
        this.projectId = projectId;
    }

    public ProjectId getProjectId() {
        return projectId;
    }
}
