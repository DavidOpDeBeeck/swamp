package de.daxu.swamp.scheduling.command.projectinstance;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.core.strategy.ServerLocatorStrategy;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.projectinstance.event.ProjectInstanceInitializedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static de.daxu.swamp.deploy.configuration.ContainerConfiguration.Builder.aContainerConfiguration;

@Component
@SuppressWarnings( "unused" )
public class ProjectInstanceProcessManager {

    private static final ServerLocatorStrategy STRATEGY = new FirstInLineStrategy();

    private final ContainerInstanceCommandService containerService;

    @Autowired
    public ProjectInstanceProcessManager( ContainerInstanceCommandService containerService ) {
        this.containerService = containerService;
    }

    @EventHandler
    public void on( ProjectInstanceInitializedEvent event ) {
        event.getContainers()
                .forEach( initializeContainerInstance() );
    }

    private BiConsumer<ContainerInstanceId, Container> initializeContainerInstance() {
        return ( containerInstanceId, container ) -> server( container )
                .ifPresent( scheduleContainerInstance( containerInstanceId, container ) );
    }

    private Consumer<Server> scheduleContainerInstance( ContainerInstanceId containerInstanceId, Container container ) {
        return server -> containerService.initialize( containerInstanceId, configuration( container ), server );
    }

    private Optional<Server> server( Container container ) {
        return STRATEGY.locate( container.getPotentialLocations() );
    }

    private ContainerConfiguration configuration( Container container ) {
        return aContainerConfiguration()
                .withPortMappings( container.getPortMappings() )
                .withEnvironmentVariables( container.getEnvironmentVariables() )
                .withRunConfiguration( container.getRunConfiguration() ).build();
    }
}
