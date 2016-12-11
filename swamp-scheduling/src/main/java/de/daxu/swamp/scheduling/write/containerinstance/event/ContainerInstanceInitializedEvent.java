package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.core.location.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;

public class ContainerInstanceInitializedEvent extends ContainerInstanceEvent {

    private final Server server;
    private final ContainerConfiguration configuration;
    private final Date dateInitialized;

    public ContainerInstanceInitializedEvent( ContainerInstanceId containerInstanceId,
                                              Server server,
                                              ContainerConfiguration configuration,
                                              Date dateInitialized ) {
        super( containerInstanceId );
        this.server = server;
        this.configuration = configuration;
        this.dateInitialized = dateInitialized;
    }

    public Server getServer() {
        return server;
    }

    public ContainerConfiguration getConfiguration() {
        return configuration;
    }

    public Date getDateInitialized() {
        return dateInitialized;
    }
}
