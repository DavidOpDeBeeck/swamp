package de.daxu.swamp.scheduler.action;

import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.service.SchedulingService;

public abstract class Action {

    private EventHandler eventHandler;
    private SchedulingService schedulingService;

    public Action( EventHandler eventHandler, SchedulingService schedulingService ) {
        this.eventHandler = eventHandler;
        this.schedulingService = schedulingService;
    }

    public abstract void execute();

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public SchedulingService getSchedulingService() {
        return schedulingService;
    }

}
