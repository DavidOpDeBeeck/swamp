package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.scheduling.command.projectinstance.event.ProjectInstanceInitializedEvent;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.query.project.ProjectView.Builder.aProjectView;
import static de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView.Builder.aProjectInstanceView;

@Component
@SuppressWarnings( "unused" )
public class ProjectViewEventHandler {

    private final ProjectViewRepository projectViewRepository;

    @Autowired
    public ProjectViewEventHandler( ProjectViewRepository projectViewRepository ) {
        this.projectViewRepository = projectViewRepository;
    }

    @EventHandler
    void on( ProjectInstanceInitializedEvent event ) {
        ProjectView project = projectViewRepository.getByName( event.getName() );

        if( project == null )
            project = createProjectView( event );

        project.addProjectInstance( createProjectInstanceView( event ) );

        projectViewRepository.save( project );
    }

    private ProjectView createProjectView( ProjectInstanceInitializedEvent event ) {
        ProjectView project = aProjectView()
                .withName( event.getName() )
                .withDescription( event.getDescription() )
                .build();
        return projectViewRepository.save( project );
    }

    private ProjectInstanceView createProjectInstanceView( ProjectInstanceInitializedEvent event ) {
        return aProjectInstanceView()
                .withProjectInstanceId( event.getProjectInstanceId() )
                .withInitializedAt( event.getInitializedAt() )
                .withContainers( event.getContainers().keySet() )
                .build();
    }
}
