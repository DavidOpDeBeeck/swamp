package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AbstractContainerInstanceEvent implements ContainerInstanceEvent {

    private final ContainerInstanceId containerInstanceId;
    private final BuildId buildId;
    private final EventMetaData eventMetaData;

    public AbstractContainerInstanceEvent(ContainerInstanceId containerInstanceId,
                                          BuildId buildId,
                                          EventMetaData eventMetaData) {
        this.containerInstanceId = containerInstanceId;
        this.buildId = buildId;
        this.eventMetaData = eventMetaData;
    }

    @Override
    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    @Override
    public BuildId getBuildId() {
        return buildId;
    }

    @Override
    public EventMetaData getEventMetaData() {
        return eventMetaData;
    }
}
