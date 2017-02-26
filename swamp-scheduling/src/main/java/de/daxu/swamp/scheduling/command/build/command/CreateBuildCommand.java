package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class CreateBuildCommand extends BuildCommand {

    private final ProjectId projectId;
    private final int sequence;
    private final Set<Container> containers;

    public CreateBuildCommand(BuildId buildId,
                              ProjectId projectId,
                              int sequence,
                              Set<Container> containers) {
        super(buildId);
        this.projectId = projectId;
        this.sequence = sequence;
        this.containers = containers;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public int getSequence() {
        return sequence;
    }

    public Set<Container> getContainers() {
        return containers;
    }
}
