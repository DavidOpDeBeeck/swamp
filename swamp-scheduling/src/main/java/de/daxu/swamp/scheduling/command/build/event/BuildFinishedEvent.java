package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.build.BuildStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class BuildFinishedEvent
        extends BuildEvent
        implements BuildStatusChangedEvent {

    public BuildFinishedEvent(BuildId buildId, ProjectId projectId, EventMetaData eventMetaData) {
        super(buildId, projectId, eventMetaData);
    }

    @Override
    public BuildStatus getBuildStatus() {
        return BuildStatus.FINISHED;
    }
}
