package de.daxu.swamp.scheduling.command.serverinstance.event;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.scheduling.command.serverinstance.ServerInstanceId;

public class ServerInstanceCreatedEvent extends ServerInstanceEvent {

    private final Server server;

    public ServerInstanceCreatedEvent( ServerInstanceId serverInstanceId, Server server ) {
        super( serverInstanceId );
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}