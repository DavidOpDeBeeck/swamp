package de.daxu.swamp.scheduling.notify.project;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.project.event.ProjectEvent;
import de.daxu.swamp.scheduling.notify.EventNotification;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@SuppressWarnings("unused")
@EventListener(replayable = false)
public class ProjectNotifyEventListener {

    private static final String TOPIC = "/topic/project-updates";

    private final SimpMessageSendingOperations messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(ProjectNotifyEventListener.class);

    @Autowired
    public ProjectNotifyEventListener(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventHandler
    void on(ProjectEvent event) {
        publish(event);
    }

    private void publish(ProjectEvent event) {
        logger.debug("Sending event : {} over topic: {}", event.getClass().getSimpleName(), TOPIC);
        this.messagingTemplate.convertAndSend(TOPIC, new EventNotification(event));
    }
}