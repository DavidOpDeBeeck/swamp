package de.daxu.swamp.scheduling.read.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.write.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceView.ContainerInstanceViewBuilder.aContainerInstanceView;
import static de.daxu.swamp.scheduling.read.containerinstance.ServerView.ServerViewBuilder.aServerView;

@Component
@SuppressWarnings( "unused" )
public class ContainerInstanceViewEventHandler {

    @Autowired
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @EventHandler
    void on( ContainerInstanceScheduledEvent event ) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getContainerInstanceId() )
                .withDateScheduled( event.getDateScheduled() )
                .withServer( aServerView()
                        .withName( event.getServer().getName() )
                        .withIp( event.getServer().getIp() )
                        .withCACertificate( event.getServer().getCACertificate() )
                        .withCertificate( event.getServer().getCertificate() )
                        .withKey( event.getServer().getKey() )
                        .build() )
                .withStatus( ContainerInstanceStatus.SCHEDULED )
                .build();
        containerInstanceViewRepository.save( view );
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.setInternalContainerId( event.getInternalContainerId() );
        view.setInternalContainerName( event.getInternalContainerName() );
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
    void on( ContainerInstanceLogReceivedEvent event ) {
        ContainerInstanceView view = getByContainerInstanceId( event );

        view.addLog( event.getLog() );

        containerInstanceViewRepository.save( view );
    }

    private ContainerInstanceView getByContainerInstanceId( ContainerInstanceEvent event ) {
        return containerInstanceViewRepository.getByContainerInstanceId( event.getContainerInstanceId() );
    }
}
