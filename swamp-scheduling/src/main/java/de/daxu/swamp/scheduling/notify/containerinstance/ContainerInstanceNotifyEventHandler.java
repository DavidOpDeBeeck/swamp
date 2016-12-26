package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.common.jackson.SwampObjectMapper;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings( "unused" )
public class ContainerInstanceNotifyEventHandler {

    private final SwampObjectMapper objectMapper;
    private final SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger( ContainerInstanceNotifyEventHandler.class );

    @Autowired
    public ContainerInstanceNotifyEventHandler( SimpMessageSendingOperations messagingTemplate ) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = new SwampObjectMapper();
    }

    @EventHandler
    void on( ContainerInstanceEvent event ) {
        publish( event );
    }

    private void publish( ContainerInstanceEvent event ) {
        logger.info( "\n **************************** \n" +
                objectMapper.toJSON( event ) +
                "\n ****************************" );
        this.messagingTemplate.convertAndSend( "/topic/container-updates", createNotification( event ) );
    }

    private ContainerInstanceNotification createNotification( ContainerInstanceEvent event ) {
        return new ContainerInstanceNotification( event );
    }
}
