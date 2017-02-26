package de.daxu.swamp.scheduling.command.project.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class ProjectCreatedEvent extends ProjectEvent {

    private final String name;
    private final String description;

    public ProjectCreatedEvent(ProjectId projectId,
                               EventMetaData eventMetaData,
                               String name,
                               String description) {
        super(projectId, eventMetaData);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
