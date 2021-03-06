package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceStatusChangedEvent;
import de.daxu.swamp.scheduling.command.project.event.BuildScheduledEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unused")
@EventListener(replayable = false)
public class BuildProcessManager {

    private final BuildCommandService buildCommandService;

    @Autowired
    public BuildProcessManager(BuildCommandService buildCommandService) {
        this.buildCommandService = buildCommandService;
    }

    @EventHandler
    public void on(BuildScheduledEvent event) {
        buildCommandService.createBuild(
                event.getProjectId(),
                event.getSequence(),
                event.getContainerTemplates());
    }

    @EventHandler
    public void on(ContainerInstanceStatusChangedEvent event) {
        buildCommandService.updateContainerInstanceStatus(
                event.getBuildId(),
                event.getContainerInstanceId(),
                event.getContainerInstanceStatus());
    }
}
