package de.daxu.swamp.scheduling.query.log;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreationLogReceivedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceRunningLogReceivedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static de.daxu.swamp.scheduling.query.log.LogView.Builder.aLogView;

@EventListener
@Transactional
@SuppressWarnings("unused")
public class LogViewEventListener {

    private final LogViewRepository logViewRepository;

    @Autowired
    public LogViewEventListener(LogViewRepository logViewRepository) {
        this.logViewRepository = logViewRepository;
    }

    @EventHandler
    void on(ContainerInstanceInitializedEvent event) {
        logViewRepository.save(aLogView()
                .withContainerInstanceId(event.getContainerInstanceId())
                .build());
    }

    @EventHandler
    void on(ContainerInstanceCreationLogReceivedEvent event) {
        LogView view = getByContainerInstanceId(event);
        view.addCreationLog(event.getLog());
        logViewRepository.save(view);
    }

    @EventHandler
    void on(ContainerInstanceRunningLogReceivedEvent event) {
        LogView view = getByContainerInstanceId(event);
        view.addRunningLog(event.getLog());
        logViewRepository.save(view);
    }

    private LogView getByContainerInstanceId(ContainerInstanceEvent event) {
        return logViewRepository.getByContainerInstanceId(event.getContainerInstanceId());
    }
}
