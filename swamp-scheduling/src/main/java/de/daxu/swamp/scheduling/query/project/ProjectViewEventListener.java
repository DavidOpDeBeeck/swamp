package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.axon.EventListener;
import de.daxu.swamp.scheduling.command.build.event.BuildInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.query.build.BuildView;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

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
    void on(BuildInitializedEvent event) {
        ProjectView project = getByName(event);

        project.addBuild(createBuildView(event));
        project.setBuildSequence(event.getSequence());

        projectViewRepository.save(project);
    }

    private ProjectView getByName(BuildInitializedEvent event) {
        ProjectView project = projectViewRepository.getByName(event.getProjectName());
        return project == null ? createProjectView(event) : project;
    }

    private ProjectView createProjectView(BuildInitializedEvent event) {
        ProjectView project = aProjectView()
                .withName(event.getProjectName())
                .withDescription(event.getProjectDescription())
                .withBuildSequence(event.getSequence())
                .build();
        return projectViewRepository.save(project);
    }

    private BuildView createBuildView(BuildInitializedEvent event) {
        return aBuildView()
                .withBuildId(event.getBuildId())
                .withSequence(event.getSequence())
                .withInitializedAt(event.getEventMetaData().getCreatedAt())
                .withContainers(mapContainers(event))
                .build();
    }

    private Map<ContainerInstanceId, ContainerInstanceStatus> mapContainers(BuildInitializedEvent event) {
        return event.getContainers()
                .stream()
                .collect(Collectors.toMap(id -> id, id -> ContainerInstanceStatus.INITIALIZED));
    }
}
