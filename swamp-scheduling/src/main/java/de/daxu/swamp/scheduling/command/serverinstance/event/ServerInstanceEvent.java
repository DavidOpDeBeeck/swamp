package de.daxu.swamp.scheduling.command.serverinstance.event;

import de.daxu.swamp.scheduling.command.serverinstance.ServerInstanceId;

public class ServerInstanceEvent {

    private ServerInstanceId serverInstanceId;

    public ServerInstanceEvent( ServerInstanceId serverInstanceId ) {
        this.serverInstanceId = serverInstanceId;
    }

    public ServerInstanceId getServerInstanceId() {
        return serverInstanceId;
    }
}
