package de.daxu.swamp.scheduling.command.build.event;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.project.ProjectId;

public class BuildContainerInstanceScheduledEvent extends ContainerInstanceBuildEvent {

    private final ContainerConfiguration configuration;
    private final Server server;

    public BuildContainerInstanceScheduledEvent(BuildId buildId,
                                                ProjectId projectId,
                                                EventMetaData eventMetaData,
                                                ContainerInstanceId containerInstanceId,
                                                ContainerConfiguration configuration,
                                                Server server) {
        super(buildId, projectId, eventMetaData, containerInstanceId);
        this.configuration = configuration;
        this.server = server;
    }

    public ContainerConfiguration getConfiguration() {
        return configuration;
    }

    public Server getServer() {
        return server;
    }
}
