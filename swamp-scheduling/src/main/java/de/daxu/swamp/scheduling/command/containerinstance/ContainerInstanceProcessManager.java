package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.BuildCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.ERRORS_ON_ACTION;

@EventListener(replayable = false)
@SuppressWarnings("unused")
public class ContainerInstanceProcessManager {


    private final BuildCommandService buildCommandService;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ContainerInstanceProcessManager(BuildCommandService buildCommandService,
                                           ContainerInstanceCommandService containerInstanceCommandService) {
        this.buildCommandService = buildCommandService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @EventHandler
    public void on(ContainerInstanceInitializedEvent event) {
        buildCommandService.updateContainerInstanceStatus(event.getBuildId(), event.getContainerInstanceId(), INITIALIZED);
        containerInstanceCommandService.create(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceCreatedSucceededEvent event) {
        buildCommandService.updateContainerInstanceStatus(event.getBuildId(), event.getContainerInstanceId(), CREATED);
        containerInstanceCommandService.start(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceCreatedFailedEvent event) {
        containerInstanceCommandService.remove(event.getContainerInstanceId(), ERRORS_ON_ACTION);
    }

    @EventHandler
    public void on(ContainerInstanceStartedSucceededEvent event) {
        buildCommandService.updateContainerInstanceStatus(event.getBuildId(), event.getContainerInstanceId(), STARTED);
        containerInstanceCommandService.startLogging(event.getContainerInstanceId());
    }

    @EventHandler
    public void on(ContainerInstanceStartedFailedEvent event) {
        containerInstanceCommandService.remove(event.getContainerInstanceId(), ERRORS_ON_ACTION);
    }

    @EventHandler
    public void on(ContainerInstanceRemovedEvent event) {
        buildCommandService.updateContainerInstanceStatus(event.getBuildId(), event.getContainerInstanceId(), REMOVED);
    }
}
