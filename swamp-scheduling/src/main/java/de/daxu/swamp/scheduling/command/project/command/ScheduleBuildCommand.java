package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class ScheduleBuildCommand extends ProjectCommand {

    private final Set<Container> containers;

    public ScheduleBuildCommand(ProjectId projectId, Set<Container> containers) {
        super(projectId);
        this.containers = containers;
    }

    public Set<Container> getContainers() {
        return containers;
    }
}
