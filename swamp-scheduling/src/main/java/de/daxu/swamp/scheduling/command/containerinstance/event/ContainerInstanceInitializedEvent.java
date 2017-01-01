package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

import java.time.LocalDateTime;

public class ContainerInstanceInitializedEvent extends ContainerInstanceEvent {

    private final Server server;
    private final ContainerConfiguration configuration;
    private final LocalDateTime initializedAt;

    public ContainerInstanceInitializedEvent( ContainerInstanceId containerInstanceId,
                                              Server server,
                                              ContainerConfiguration configuration,
                                              LocalDateTime initializedAt ) {
        super( containerInstanceId );
        this.server = server;
        this.configuration = configuration;
        this.initializedAt = initializedAt;
    }

    public Server getServer() {
        return server;
    }

    public ContainerConfiguration getConfiguration() {
        return configuration;
    }

    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }
}
