package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class CreateBuildCommand extends BuildCommand {

    private final ProjectId projectId;
    private final int sequence;
    private final Set<ContainerTemplate> containerTemplates;

    public CreateBuildCommand(BuildId buildId,
                              ProjectId projectId,
                              int sequence,
                              Set<ContainerTemplate> containerTemplates) {
        super(buildId);
        this.projectId = projectId;
        this.sequence = sequence;
        this.containerTemplates = containerTemplates;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public int getSequence() {
        return sequence;
    }

    public Set<ContainerTemplate> getContainerTemplates() {
        return containerTemplates;
    }
}
