package de.daxu.swamp.scheduling.command.project;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.event.BuildCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unused")
@EventListener(replayable = false)
public class ProjectProcessManager {

    private final ProjectCommandService projectCommandService;

    @Autowired
    public ProjectProcessManager(ProjectCommandService projectCommandService) {
        this.projectCommandService = projectCommandService;
    }

    @EventHandler
    public void on(BuildCreatedEvent event) {
        projectCommandService.addBuild(event.getProjectId(), event.getBuildId());
    }
}
