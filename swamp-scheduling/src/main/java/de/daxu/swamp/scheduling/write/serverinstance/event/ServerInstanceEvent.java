package de.daxu.swamp.scheduling.write.serverinstance.event;

import de.daxu.swamp.scheduling.write.serverinstance.ServerInstanceId;

public class ServerInstanceEvent {

    private ServerInstanceId serverInstanceId;

    public ServerInstanceEvent( ServerInstanceId serverInstanceId ) {
        this.serverInstanceId = serverInstanceId;
    }

    public ServerInstanceId getServerInstanceId() {
        return serverInstanceId;
    }
}
