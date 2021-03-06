package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.Event;
import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class BuildEvent implements Event {

    private final BuildId buildId;
    private final ProjectId projectId;
    private final EventMetaData eventMetaData;

    public BuildEvent(BuildId buildId, ProjectId projectId, EventMetaData eventMetaData) {
        this.buildId = buildId;
        this.projectId = projectId;
        this.eventMetaData = eventMetaData;
    }

    public ProjectId getProjectId() {
        return projectId;
    }

    public BuildId getBuildId() {
        return buildId;
    }

    @Override
    public EventMetaData getEventMetaData() {
        return eventMetaData;
    }
}
