package de.daxu.swamp.scheduling.write.serverinstance.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.scheduling.write.serverinstance.ServerInstanceId;

public class CreateServerInstanceCommand extends ServerInstanceCommand {

    private final Server server;

    public CreateServerInstanceCommand( ServerInstanceId serverInstanceId, Server server ) {
        super( serverInstanceId );
        this.server = server;
    }

    public Server getServer() {
        return server;
    }
}
