package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings( "unused" )
public class ContainerInstanceNotifyEventHandler {

    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public ContainerInstanceNotifyEventHandler( SimpMessageSendingOperations messagingTemplate ) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    void on( ContainerInstanceEvent event ) {
        publish( event );
    }

    private void publish( ContainerInstanceEvent event ) {
        this.messagingTemplate.convertAndSend( "/topic/container-updates", createNotification( event ) );
    }

    private ContainerInstanceNotification createNotification( ContainerInstanceEvent event ) {
        return new ContainerInstanceNotification( event );
    }
}
