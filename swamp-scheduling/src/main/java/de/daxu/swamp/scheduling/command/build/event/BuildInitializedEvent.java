package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.util.Map;

public class BuildInitializedEvent extends BuildEvent {

    private final int sequence;
    private final String projectName;
    private final String projectDescription;
    private final Map<ContainerInstanceId, Container> containers;

    public BuildInitializedEvent(BuildId buildId,
                                 EventMetaData eventMetaData,
                                 int sequence,
                                 String projectName,
                                 String projectDescription,
                                 Map<ContainerInstanceId, Container> containers) {
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

    public Map<ContainerInstanceId, Container> getContainers() {
        return containers;
    }
}
