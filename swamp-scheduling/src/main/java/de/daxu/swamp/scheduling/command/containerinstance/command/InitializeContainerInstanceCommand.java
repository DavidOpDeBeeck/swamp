package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class InitializeContainerInstanceCommand extends ContainerInstanceCommand {

    private final Server server;
    private final ContainerConfiguration configuration;

    public InitializeContainerInstanceCommand( ContainerInstanceId containerInstanceId,
                                               ContainerConfiguration configuration,
                                               Server server ) {
        super( containerInstanceId );
        this.server = server;
        this.configuration = configuration;
    }

    public Server getServer() {
        return server;
    }

    public ContainerConfiguration getConfiguration() {
        return configuration;
    }
}
