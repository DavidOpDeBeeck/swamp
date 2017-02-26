package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class AddBuildCommand extends ProjectCommand {

    private final BuildId buildId;

    public AddBuildCommand(ProjectId projectId, BuildId buildId) {
        super(projectId);
        this.buildId = buildId;
    }

    public BuildId getBuildId() {
        return buildId;
    }
}
