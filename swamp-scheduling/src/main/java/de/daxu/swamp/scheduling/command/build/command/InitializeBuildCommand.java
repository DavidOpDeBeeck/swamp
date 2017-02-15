package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.BuildId;

public class InitializeBuildCommand extends BuildCommand {

    private final Project project;
    private final int sequence;

    public InitializeBuildCommand(BuildId buildId, Project project, int sequence) {
        super(buildId);
        this.project = project;
        this.sequence = sequence;
    }

    public Project getProject() {
        return project;
    }

    public int getSequence() {
        return sequence;
    }
}
