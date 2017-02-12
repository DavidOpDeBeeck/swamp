package de.daxu.swamp.scheduling.command.containerinstance.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;

public class ContainerInstanceInitializedEvent extends AbstractContainerInstanceEvent {

    private final BuildId buildId;
    private final Server server;
    private final ContainerConfiguration configuration;

    public ContainerInstanceInitializedEvent(ContainerInstanceId containerInstanceId,
                                             EventMetaData eventMetaData,
                                             BuildId buildId,
                                             Server server,
                                             ContainerConfiguration configuration) {
        super( containerInstanceId, eventMetaData);
        this.buildId = buildId;
        this.server = server;
        this.configuration = configuration;
    }

    public BuildId getBuildId() {
        return buildId;
    }

    public Server getServer() {
        return server;
    }

    public ContainerConfiguration getConfiguration() {
        return configuration;
    }

}
