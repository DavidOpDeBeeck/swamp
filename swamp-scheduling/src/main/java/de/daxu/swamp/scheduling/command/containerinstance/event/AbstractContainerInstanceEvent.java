package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class AbstractContainerInstanceEvent implements ContainerInstanceEvent {

    private final ContainerInstanceId containerInstanceId;
    private final EventMetaData eventMetaData;

    public AbstractContainerInstanceEvent(ContainerInstanceId containerInstanceId, EventMetaData eventMetaData) {
        this.containerInstanceId = containerInstanceId;
        this.eventMetaData = eventMetaData;
    }

    @Override
    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    @Override
    public EventMetaData getEventMetaData() {
        return eventMetaData;
    }
}
