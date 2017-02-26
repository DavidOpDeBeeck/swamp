package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.build.BuildStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class BuildCreatedEvent
        extends BuildEvent
        implements BuildStatusChangedEvent {

    private final int sequence;

    public BuildCreatedEvent(BuildId buildId,
                             ProjectId projectId,
                             EventMetaData eventMetaData,
                             int sequence) {
        super(buildId, projectId, eventMetaData);
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    @Override
    public BuildStatus getBuildStatus() {
        return BuildStatus.INPROGRESS;
    }
}
