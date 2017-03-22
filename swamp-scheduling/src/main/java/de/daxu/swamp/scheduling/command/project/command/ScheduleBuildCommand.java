package de.daxu.swamp.scheduling.command.project.command;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class ScheduleBuildCommand extends ProjectCommand {

    private final Set<ContainerTemplate> containerTemplates;

    public ScheduleBuildCommand(ProjectId projectId, Set<ContainerTemplate> containerTemplates) {
        super(projectId);
        this.containerTemplates = containerTemplates;
    }

    public Set<ContainerTemplate> getContainerTemplates() {
        return containerTemplates;
    }
}
