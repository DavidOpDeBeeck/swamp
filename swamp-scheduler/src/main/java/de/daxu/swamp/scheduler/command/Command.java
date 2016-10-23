package de.daxu.swamp.scheduler.command;

import de.daxu.swamp.scheduler.event.EventHandler;

public abstract class Command<INPUT> {

    private EventHandler eventHandler;

    public Command( EventHandler eventHandler ) {
        this.eventHandler = eventHandler;
    }

    public abstract void execute( INPUT instance );

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}
