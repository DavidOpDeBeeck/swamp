package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.scheduling.view.containerinstance.ContainerInstanceViewRepository;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceNotifyEventHandler {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private ContainerInstanceViewRepository containerInstanceViewRepository;

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        publish( event );
    }

    private void publish( ContainerInstanceEvent event ) {
        this.messagingTemplate.convertAndSend( "/topic/container-updates", new ContainerInstanceNotification( event ) );
    }
}
