package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.BuildId;

public class InitializeBuildCommand extends BuildCommand {

    private final Project project;

    public InitializeBuildCommand(BuildId buildId, Project project) {
        super(buildId);
        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}
