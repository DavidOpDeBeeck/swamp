package de.daxu.swamp.scheduling.command.projectinstance;

import com.google.common.collect.Maps;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.projectinstance.command.InitializeProjectInstanceCommand;
import de.daxu.swamp.scheduling.command.projectinstance.event.ProjectInstanceInitializedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@SuppressWarnings( "unused" )
public class ProjectInstance extends AbstractAnnotatedAggregateRoot<ProjectInstanceId> {

    @AggregateIdentifier
    private ProjectInstanceId projectInstanceId;

    private Set<ContainerInstanceId> containers;

    ProjectInstance() {
    }

    @CommandHandler
    public ProjectInstance( InitializeProjectInstanceCommand command ) {
        Project project = command.getProject();
        Map<ContainerInstanceId, Container> containers = Maps.newHashMap();

        project.getContainers()
                .forEach( container -> containers.put( ContainerInstanceId.random(), container ) );

        apply( new ProjectInstanceInitializedEvent(
                command.getProjectInstanceId(), project.getName(), project.getDescription(), containers, new Date() ) );
    }

    @EventHandler
    void on( ProjectInstanceInitializedEvent event ) {
        this.projectInstanceId = event.getProjectInstanceId();
        this.containers = event.getContainers().keySet();
    }
}
