package de.daxu.swamp.scheduling.notify.containerinstance;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@EventListener(replayable = false)
@SuppressWarnings("unused")
public class ContainerInstanceNotifyEventListener {

    private static final String TOPIC = "/topic/container-updates";

    private final SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(ContainerInstanceNotifyEventListener.class);

    @Autowired
    public ContainerInstanceNotifyEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    void on(ContainerInstanceEvent event) {
        publish(event);
    }

    private void publish(ContainerInstanceEvent event) {
        logger.info("Sending event : {} over topic: {}", event.getClass().getSimpleName(), TOPIC);
        this.messagingTemplate.convertAndSend(TOPIC, createNotification(event));
    }

    private ContainerInstanceNotification createNotification(ContainerInstanceEvent event) {
        return new ContainerInstanceNotification(event);
    }
}
