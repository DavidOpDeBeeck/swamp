package de.daxu.swamp.scheduling.command.project.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.scheduling.command.project.ProjectId;

import java.util.Set;

public class BuildScheduledEvent extends ProjectEvent {

    private final int sequence;
    private final Set<ContainerTemplate> containerTemplates;

    public BuildScheduledEvent(ProjectId projectId,
                               EventMetaData eventMetaData,
                               int sequence,
                               Set<ContainerTemplate> containerTemplates) {
        super(projectId, eventMetaData);
        this.sequence = sequence;
        this.containerTemplates = containerTemplates;
    }

    public int getSequence() {
        return sequence;
    }

    public Set<ContainerTemplate> getContainerTemplates() {
        return containerTemplates;
    }
}
