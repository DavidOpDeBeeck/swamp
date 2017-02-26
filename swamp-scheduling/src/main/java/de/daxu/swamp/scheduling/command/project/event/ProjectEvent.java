package de.daxu.swamp.scheduling.command.project.event;

import de.daxu.swamp.common.cqrs.Event;
import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class ProjectEvent implements Event {

    private final ProjectId projectId;
    private final EventMetaData eventMetaData;

    public ProjectEvent(ProjectId projectId, EventMetaData eventMetaData) {
        this.projectId = projectId;
        this.eventMetaData = eventMetaData;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    @Override
    public EventMetaData getEventMetaData() {
        return eventMetaData;
    }
}
