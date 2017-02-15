package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.axon.ProcessManager;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.core.strategy.ServerLocatorStrategy;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.scheduling.command.build.event.BuildInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@ProcessManager
@SuppressWarnings("unused")
public class BuildProcessManager {

    private static final ServerLocatorStrategy STRATEGY = new FirstInLineStrategy();

    private final ContainerInstanceCommandService containerService;

    @Autowired
    public BuildProcessManager(ContainerInstanceCommandService containerService) {
        this.containerService = containerService;
    }

    @EventHandler
    public void on(BuildInitializedEvent event) {
        event.getContainers()
                .forEach(initializeContainerInstance(event.getBuildId()));
    }

    private BiConsumer<ContainerInstanceId, Container> initializeContainerInstance(BuildId buildId) {
        return (containerInstanceId, container) ->
                locateAvailableServer(container)
                        .ifPresent(scheduleContainerInstance(containerInstanceId, container, buildId));
    }

    private Optional<Server> locateAvailableServer(Container container) {
        return STRATEGY.locate(container.getPotentialLocations());
    }

    private Consumer<Server> scheduleContainerInstance(ContainerInstanceId containerInstanceId, Container container, BuildId buildId) {
        return server -> containerService.initialize(containerInstanceId, buildId, ContainerConfiguration.of(GroupId.of(buildId.getValue()), container), server);
    }
}
