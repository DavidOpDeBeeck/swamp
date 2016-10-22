package de.daxu.swamp.core.scheduler.command;

import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.command.container.CreateCommand;
import de.daxu.swamp.core.scheduler.command.instance.RestartCommand;
import de.daxu.swamp.core.scheduler.command.instance.StartCommand;
import de.daxu.swamp.core.scheduler.command.instance.StopCommand;

public class CommandFactory {

    public static CreateCommand createCommand( Server server ) {
        return new CreateCommand( server );
    }

    public static StartCommand startCommand() {
        return new StartCommand();
    }

    public static RestartCommand restartCommand() {
        return new RestartCommand();
    }

    public static StopCommand stopCommand() {
        return new StopCommand();
    }

}
