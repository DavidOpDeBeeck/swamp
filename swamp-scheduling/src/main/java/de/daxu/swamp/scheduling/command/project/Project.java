package de.daxu.swamp.scheduling.command.project;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.scheduling.command.project.command.CreateProjectCommand;
import de.daxu.swamp.scheduling.command.project.command.ScheduleBuildCommand;
import de.daxu.swamp.scheduling.command.project.event.BuildScheduledEvent;
import de.daxu.swamp.scheduling.command.project.event.ProjectCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("unused")
public class Project extends AbstractAnnotatedAggregateRoot<ProjectId> {

    @AggregateIdentifier
    private ProjectId projectId;
    private int sequence;

    @Autowired
    private EventMetaDataFactory eventMetaDataFactory;

    Project() {
    }

    @CommandHandler
    public Project(CreateProjectCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ProjectCreatedEvent(
                command.getProjectId(),
                eventMetaDataFactory.create(),
                command.getName(),
                command.getDescription()));
    }

    @CommandHandler
    public void scheduleBuild(ScheduleBuildCommand command) {
        apply(new BuildScheduledEvent(
                projectId,
                eventMetaDataFactory.create(),
                sequence,
                command.getContainers()));
    }

    @EventSourcingHandler
    void on(ProjectCreatedEvent event) {
        this.projectId = event.getProjectId();
        this.sequence = 0;
    }

    @EventSourcingHandler
    void on(BuildScheduledEvent event) {
        this.sequence++;
    }
}
