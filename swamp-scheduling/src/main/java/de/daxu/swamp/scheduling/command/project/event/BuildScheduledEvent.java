package de.daxu.swamp.scheduling.command.project.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class BuildScheduledEvent extends ProjectEvent {

    private final int sequence;
    private final Set<Container> containers;

    public BuildScheduledEvent(ProjectId projectId,
                               EventMetaData eventMetaData,
                               int sequence,
                               Set<Container> containers) {
        super(projectId, eventMetaData);
        this.sequence = sequence;
        this.containers = containers;
    }

    public int getSequence() {
        return sequence;
    }

    public Set<Container> getContainers() {
        return containers;
    }
}
