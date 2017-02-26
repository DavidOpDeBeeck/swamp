package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.event.BuildCreatedEvent;
import de.daxu.swamp.scheduling.command.project.event.ProjectCreatedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import static de.daxu.swamp.scheduling.query.build.BuildView.Builder.aBuildView;
import static de.daxu.swamp.scheduling.query.project.ProjectView.Builder.aProjectView;

@EventListener
@SuppressWarnings("unused")
public class ProjectViewEventListener {

    private final ProjectViewRepository projectViewRepository;

    @Autowired
    public ProjectViewEventListener(ProjectViewRepository projectViewRepository) {
        this.projectViewRepository = projectViewRepository;
    }

    @EventHandler
    void on(ProjectCreatedEvent event) {
        ProjectView project = aProjectView()
                .withProjectId(event.getProjectId())
                .withName(event.getName())
                .withDescription(event.getDescription())
                .build();

        projectViewRepository.save(project);
    }

    @EventHandler
    void on(BuildCreatedEvent event) {
        ProjectView project = projectViewRepository.getByProjectId(event.getProjectId());

        project.addBuild(aBuildView()
                .withBuildId(event.getBuildId())
                .withSequence(event.getSequence())
                .withInitializedAt(event.getEventMetaData().getCreatedAt())
                .build());
        project.setBuildSequence(event.getSequence());

        projectViewRepository.save(project);
    }
}
