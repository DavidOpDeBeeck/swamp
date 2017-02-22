package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Set;

public class BuildInitializedEvent extends BuildEvent {

    private final int sequence;
    private final String projectName;
    private final String projectDescription;
    private final Set<ContainerInstanceId> containers;

    public BuildInitializedEvent(BuildId buildId,
                                 EventMetaData eventMetaData,
                                 int sequence,
                                 String projectName,
                                 String projectDescription,
                                 Set<ContainerInstanceId> containers) {
        super(buildId, eventMetaData);
        this.sequence = sequence;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.containers = containers;
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

    public Set<ContainerInstanceId> getContainers() {
        return containers;
    }
}
