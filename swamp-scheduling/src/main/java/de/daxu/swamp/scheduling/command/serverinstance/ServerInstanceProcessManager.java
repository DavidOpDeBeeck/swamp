package de.daxu.swamp.scheduling.command.serverinstance;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceStartedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings( "unused" )
public class ServerInstanceProcessManager {

    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ServerInstanceProcessManager( ContainerInstanceCommandService containerInstanceCommandService ) {
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @EventHandler
    public void on( ContainerInstanceInitializedEvent event ) {
        containerInstanceCommandService.create( event.getContainerInstanceId() );
    }

    @EventHandler
    public void on( ContainerInstanceCreatedEvent event ) {
        containerInstanceCommandService.start( event.getContainerInstanceId() );
    }

    @EventHandler
    public void on( ContainerInstanceStartedEvent event ) {
        containerInstanceCommandService.startLogging( event.getContainerInstanceId() );
    }
}
