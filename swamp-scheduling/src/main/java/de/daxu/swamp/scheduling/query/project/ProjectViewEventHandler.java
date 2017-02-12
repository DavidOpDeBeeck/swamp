package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.scheduling.command.build.event.BuildInitializedEvent;
import de.daxu.swamp.scheduling.query.build.BuildStatus;
import de.daxu.swamp.scheduling.query.build.BuildView;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.query.build.BuildView.Builder.aBuildView;
import static de.daxu.swamp.scheduling.query.project.ProjectView.Builder.aProjectView;

@Component
@SuppressWarnings("unused")
public class ProjectViewEventHandler {

    private final ProjectViewRepository projectViewRepository;

    @Autowired
    public ProjectViewEventHandler(ProjectViewRepository projectViewRepository) {
        this.projectViewRepository = projectViewRepository;
    }

    @EventHandler
    void on(BuildInitializedEvent event) {
        ProjectView project = projectViewRepository.getByName(event.getProjectName());

        if(project == null)
            project = createProjectView(event);

        project.addBuild(createBuildView(event));

        projectViewRepository.save(project);
    }

    private ProjectView createProjectView(BuildInitializedEvent event) {
        ProjectView project = aProjectView()
                .withName(event.getProjectName())
                .withDescription(event.getProjectDescription())
                .build();
        return projectViewRepository.save(project);
    }

    private BuildView createBuildView(BuildInitializedEvent event) {
        return aBuildView()
                .withBuildId(event.getBuildId())
                .withSequence(event.getSequence())
                .withStatus(BuildStatus.INPROGRESS)
                .withInitializedAt(event.getEventMetaData().getCreatedAt())
                .withContainers(event.getContainers().keySet())
                .build();
    }
}
