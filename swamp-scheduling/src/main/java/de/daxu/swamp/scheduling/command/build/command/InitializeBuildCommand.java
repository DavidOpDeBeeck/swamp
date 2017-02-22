package de.daxu.swamp.scheduling.command.build.command;

import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class InitializeBuildCommand extends BuildCommand {

    private final int sequence;
    private final String projectName;
    private final String projectDescription;
    private final Set<ContainerInstanceId> containerInstanceIds;

    public InitializeBuildCommand(BuildId buildId,
                                  int sequence,
                                  String projectName,
                                  String projectDescription,
                                  Set<ContainerInstanceId> containerInstanceIds) {
        super(buildId);
        this.sequence = sequence;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.containerInstanceIds = containerInstanceIds;
    }

    public int getSequence() {
        return sequence;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public Set<ContainerInstanceId> getContainerInstanceIds() {
        return containerInstanceIds;
    }
}
