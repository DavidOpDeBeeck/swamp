package de.daxu.swamp.scheduling.command.projectinstance.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;

public class InitializeProjectInstanceCommand extends ProjectInstanceCommand {

    private final Project project;

    public InitializeProjectInstanceCommand( ProjectInstanceId projectInstanceId,
                                             Project project ) {
        super( projectInstanceId );
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
