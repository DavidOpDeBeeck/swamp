package de.daxu.swamp.scheduling.notify;

import de.daxu.swamp.common.cqrs.AbstractEvent;
import de.daxu.swamp.scheduling.view.ContainerInstanceViewRepository;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreated;
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
    void on( ContainerInstanceCreated event ) {
        publish( event );
    }

    private void publish( AbstractEvent<ContainerInstanceId> event ) {
        this.messagingTemplate.convertAndSend( "/topic/container-updates", new ContainerInstanceNotification( event ));
    }
}
