package de.daxu.swamp.scheduling.command.projectinstance.command;

import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ProjectInstanceCommand {

    @TargetAggregateIdentifier
    private ProjectInstanceId projectInstanceId;

    public ProjectInstanceCommand( ProjectInstanceId projectInstanceId ) {
        this.projectInstanceId = projectInstanceId;
    }

    public ProjectInstanceId getProjectInstanceId() {
        return projectInstanceId;
    }
}
