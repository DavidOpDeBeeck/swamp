package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.Event;
import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;

public class BuildEvent implements Event {

    private final BuildId buildId;
    private final EventMetaData eventMetaData;

    public BuildEvent(BuildId buildId, EventMetaData eventMetaData) {
        this.buildId = buildId;
        this.eventMetaData = eventMetaData;
    }

    public BuildId getBuildId() {
        return buildId;
    }

    @Override
    public EventMetaData getEventMetaData() {
        return eventMetaData;
    }
}
