package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.BuildCommandService;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceAddedEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceScheduledEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreatedFailedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreatedSucceededEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceStartedFailedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceStartedSucceededEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.ERRORS_ON_ACTION;

@SuppressWarnings("unused")
@EventListener(replayable = false)
public class ContainerInstanceProcessManager {

    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ContainerInstanceProcessManager(BuildCommandService buildCommandService,
                                           ContainerInstanceCommandService containerInstanceCommandService) {
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @EventHandler
    public void on(BuildContainerInstanceScheduledEvent event) {
        containerInstanceCommandService.initialize(event.getBuildId(), event.getConfiguration(), event.getServer());
    }

    @EventHandler
    public void on(BuildContainerInstanceAddedEvent event) {
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
