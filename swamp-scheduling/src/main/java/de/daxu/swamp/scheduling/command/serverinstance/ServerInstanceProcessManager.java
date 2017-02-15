package de.daxu.swamp.scheduling.command.serverinstance;

import de.daxu.swamp.common.axon.ProcessManager;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.ERRORS_ON_ACTION;

@ProcessManager
@SuppressWarnings("unused")
public class ServerInstanceProcessManager {

    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ServerInstanceProcessManager(ContainerInstanceCommandService containerInstanceCommandService) {
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @EventHandler
    public void on(ContainerInstanceInitializedEvent event) {
        containerInstanceCommandService.create(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceCreatedSucceededEvent event) {
        containerInstanceCommandService.start(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceCreatedFailedEvent event) {
        containerInstanceCommandService.remove(event.getContainerInstanceId(), ERRORS_ON_ACTION);
    }

    @EventHandler
    public void on(ContainerInstanceStartedSucceededEvent event) {
        containerInstanceCommandService.startLogging(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceStartedFailedEvent event) {
        containerInstanceCommandService.remove(event.getContainerInstanceId(), ERRORS_ON_ACTION);
    }
}
