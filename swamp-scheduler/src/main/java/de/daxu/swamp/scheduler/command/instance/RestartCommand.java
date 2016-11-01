package de.daxu.swamp.scheduler.command.instance;

import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.event.EventHandler;

public class RestartCommand extends Command<ContainerInstance> {

    public RestartCommand( EventHandler eventHandler ) {
        super( eventHandler );
    }

    @Override
    public void execute( ContainerInstance instance ) {
        // TODO
    }
}
