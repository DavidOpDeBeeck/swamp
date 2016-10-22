package de.daxu.swamp.scheduler.action;

import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.manager.SchedulingManager;

public abstract class Action {

    private EventHandler eventHandler;
    private SchedulingManager schedulingManager;

    public Action( EventHandler eventHandler, SchedulingManager schedulingManager ) {
        this.eventHandler = eventHandler;
        this.schedulingManager = schedulingManager;
    }

    public abstract void execute();

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public SchedulingManager getSchedulingManager() {
        return schedulingManager;
    }
}
