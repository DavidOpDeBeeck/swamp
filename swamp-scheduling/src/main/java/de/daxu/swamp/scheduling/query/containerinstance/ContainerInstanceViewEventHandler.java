package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView.Builder.aContainerInstanceView;
import static de.daxu.swamp.scheduling.query.containerinstance.ServerView.Builder.aServerView;

@Component
@SuppressWarnings( "unused" )
public class ContainerInstanceViewEventHandler {

    private final ContainerInstanceViewRepository containerInstanceViewRepository;

    @Autowired
    public ContainerInstanceViewEventHandler( ContainerInstanceViewRepository containerInstanceViewRepository ) {
        this.containerInstanceViewRepository = containerInstanceViewRepository;
    }

    @EventHandler
    void on( ContainerInstanceInitializedEvent event ) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getContainerInstanceId() )
                .withInitializedAt( event.getInitializedAt() )
                .withServer( aServerView()
                        .withName( event.getServer().getName() )
                        .withIp( event.getServer().getIp() )
                        .build() )
                .withStatus( INITIALIZED )
                .build();
        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setCreatedAt( event.getCreatedAt() );
        view.setStatus( CREATED );
        view.setContainerId( event.getContainerId() );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceStartedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setStartedAt( event.getStartedAt() );
        view.setStatus( STARTED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceStoppedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setStoppedAt( event.getStoppedAt() );
        view.setStopReason( event.getReason() );
        view.setStatus( STOPPED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceRemovedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setRemovedAt( event.getRemovedAt() );
        view.setRemoveReason( event.getReason() );
        view.setStatus( REMOVED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceLogReceivedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.addLog( event.getLog() );

        containerInstanceViewRepository.save( view );
    }

    private ContainerInstanceView getByContainerInstanceId( ContainerInstanceEvent event ) {
        return containerInstanceViewRepository.getByContainerInstanceId( event.getContainerInstanceId() );
    }
}
