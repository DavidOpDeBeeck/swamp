package de.daxu.swamp.scheduling.notify;

import de.daxu.swamp.common.cqrs.Event;

public class EventNotification {

    private final String type;
    private final Event event;

    public EventNotification(Event event) {
        this.type = event.getClass().getSimpleName();
        this.event = event;
    }

    public String getType() {
        return type;
    }

    public Event getEvent() {
        return event;
    }
}
