package de.daxu.swamp.scheduling.command.serverinstance.command;

import de.daxu.swamp.scheduling.command.serverinstance.ServerInstanceId;
import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class ServerInstanceCommand {

    @TargetAggregateIdentifier
    private ServerInstanceId serverInstanceId;

    public ServerInstanceCommand( ServerInstanceId serverInstanceId ) {
        this.serverInstanceId = serverInstanceId;
    }

    public ServerInstanceId getServerInstanceId() {
        return serverInstanceId;
    }
}
