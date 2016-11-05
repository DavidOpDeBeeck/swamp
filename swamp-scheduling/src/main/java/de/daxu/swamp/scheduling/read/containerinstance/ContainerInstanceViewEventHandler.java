package de.daxu.swamp.scheduling.read.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
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
    void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceView view = aContainerInstanceView()
                .withContainerInstanceId( event.getContainerInstanceId() )
                .withInternalContainerId( event.getInternalContainerId() )
                .withInternalContainerName( event.getInternalContainerName() )
                .withDateCreated( event.getDateCreated() )
                .withServer( aServerView()
                        .withName( event.getServer().getName() )
                        .withIp( event.getServer().getIp() )
                        .withCACertificate( event.getServer().getCACertificate() )
                        .withCertificate( event.getServer().getCertificate() )
                        .withKey( event.getServer().getKey() )
                        .build() )
                .withStatus( ContainerInstanceStatus.CREATED )
                .build();
        containerInstanceViewRepository.save( view );
    }
}
