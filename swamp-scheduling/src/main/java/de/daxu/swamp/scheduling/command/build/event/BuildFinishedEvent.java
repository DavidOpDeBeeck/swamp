package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.build.BuildStatus;

public class BuildFinishedEvent
        extends BuildEvent
        implements BuildStatusChangedEvent {

    public BuildFinishedEvent(BuildId buildId, EventMetaData eventMetaData) {
        super(buildId, eventMetaData);
    }

    @Override
    public BuildStatus getBuildStatus() {
        return BuildStatus.FINISHED;
    }
}
