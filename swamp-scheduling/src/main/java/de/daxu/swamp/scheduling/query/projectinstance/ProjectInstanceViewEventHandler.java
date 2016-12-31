package de.daxu.swamp.scheduling.query.projectinstance;

import de.daxu.swamp.scheduling.command.projectinstance.event.ProjectInstanceEvent;
import de.daxu.swamp.scheduling.command.projectinstance.event.ProjectInstanceInitializedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView.ProjectInstanceViewBuilder.aProjectInstanceView;

@Component
@SuppressWarnings( "unused" )
public class ProjectInstanceViewEventHandler {

    private final ProjectInstanceViewRepository projectInstanceViewRepository;

    @Autowired
    public ProjectInstanceViewEventHandler( ProjectInstanceViewRepository projectInstanceViewRepository ) {
        this.projectInstanceViewRepository = projectInstanceViewRepository;
    }

    @EventHandler
    void on( ProjectInstanceInitializedEvent event ) {
        ProjectInstanceView view = aProjectInstanceView()
                .withProjectInstanceId( event.getProjectInstanceId() )
                .withName( event.getName() )
                .withDescription( event.getDescription() )
                .withDateInitialized( event.getDateInitialized() )
                .withContainers( event.getContainers().keySet() )
                .build();
        projectInstanceViewRepository.save( view );
    }

    private ProjectInstanceView getByProjectInstanceId( ProjectInstanceEvent event ) {
        return projectInstanceViewRepository.getByProjectInstanceId( event.getProjectInstanceId() );
    }
}
