package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView.ContainerInstanceViewBuilder.aContainerInstanceView;
import static de.daxu.swamp.scheduling.query.containerinstance.ServerView.ServerViewBuilder.aServerView;

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
        ContainerConfiguration configuration = event.getConfiguration();
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getContainerInstanceId() )
                .withDateInitialized( event.getDateInitialized() )
                .withServer( aServerView()
                        .withName( "" )
                        .withIp( "" )
                        .build() )
                .withStatus( ContainerInstanceStatus.INITIALIZED )
                .build();
        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setInternalContainerId( event.getInternalContainerId() );
        //view.setInternalContainerName( event.getInternalContainerName() );
        view.setDateCreated( event.getDateCreated() );
        view.setStatus( ContainerInstanceStatus.CREATED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceStartedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setDateStarted( event.getDateStarted() );
        view.setStatus( ContainerInstanceStatus.STARTED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceStoppedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setDateStopped( event.getDateStopped() );
        view.setStatus( ContainerInstanceStatus.STOPPED );

        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceRemovedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setDateRemoved( event.getDateRemoved() );
        view.setStatus( ContainerInstanceStatus.REMOVED );

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
