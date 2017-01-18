package de.daxu.swamp.scheduling.command.serverinstance;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceStartedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.ERRORS_ON_ACTION;

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
        if( event.getWarnings().isEmpty() ) {
            containerInstanceCommandService.start( event.getContainerInstanceId() );
        } else {
            containerInstanceCommandService.remove( event.getContainerInstanceId(), ERRORS_ON_ACTION );
        }
    }

    @EventHandler
    public void on( ContainerInstanceStartedEvent event ) {
        if( event.getWarnings().isEmpty() ) {
            containerInstanceCommandService.startLogging( event.getContainerInstanceId() );
        } else {
            containerInstanceCommandService.remove( event.getContainerInstanceId(), ERRORS_ON_ACTION );
        }
    }
}
