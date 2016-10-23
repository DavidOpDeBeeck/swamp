package de.daxu.swamp.scheduler.command;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduler.ProjectInstance;
import de.daxu.swamp.scheduler.command.container.CreateCommand;
import de.daxu.swamp.scheduler.command.instance.LoggingCommand;
import de.daxu.swamp.scheduler.command.instance.RestartCommand;
import de.daxu.swamp.scheduler.command.instance.StartCommand;
import de.daxu.swamp.scheduler.command.instance.StopCommand;
import de.daxu.swamp.scheduler.event.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

    @Autowired
    private EventHandler eventHandler;

    public CreateCommand createCommand( ProjectInstance projectInstance, Server server ) {
        return new CreateCommand( eventHandler, projectInstance, server );
    }

    public StartCommand startCommand() {
        return new StartCommand(eventHandler);
    }

    public RestartCommand restartCommand() {
        return new RestartCommand(eventHandler);
    }

    public StopCommand stopCommand() {
        return new StopCommand(eventHandler);
    }

    public LoggingCommand logCommand() {
        return new LoggingCommand(eventHandler);
    }

}
