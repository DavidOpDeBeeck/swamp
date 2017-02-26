package de.daxu.swamp.scheduling.notify.build;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.event.BuildEvent;
import de.daxu.swamp.scheduling.notify.EventNotification;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@SuppressWarnings("unused")
@EventListener(replayable = false)
public class BuildNotifyEventListener {

    private static final String TOPIC = "/topic/build-updates";

    private final SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(BuildNotifyEventListener.class);

    @Autowired
    public BuildNotifyEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    void on(BuildEvent event) {
        publish(event);
    }

    private void publish(BuildEvent event) {
        logger.debug("Sending event : {} over topic: {}", event.getClass().getSimpleName(), TOPIC);
        this.messagingTemplate.convertAndSend(TOPIC, new EventNotification(event));
    }
}