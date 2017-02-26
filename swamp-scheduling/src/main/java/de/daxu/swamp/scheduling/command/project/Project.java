package de.daxu.swamp.scheduling.command.project;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.scheduling.command.project.command.AddBuildCommand;
import de.daxu.swamp.scheduling.command.project.command.CreateProjectCommand;
import de.daxu.swamp.scheduling.command.project.command.ScheduleBuildCommand;
import de.daxu.swamp.scheduling.command.project.event.ProjectBuildAddedEvent;
import de.daxu.swamp.scheduling.command.project.event.ProjectBuildScheduledEvent;
import de.daxu.swamp.scheduling.command.project.event.ProjectCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings("unused")
public class Project extends AbstractAnnotatedAggregateRoot<ProjectId> {

    @AggregateIdentifier
    private ProjectId projectId;
    private int sequence;

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
    public void scheduleBuild(ScheduleBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ProjectBuildScheduledEvent(
                projectId,
                eventMetaDataFactory.create(),
                sequence,
                command.getContainers()));
    }

    @CommandHandler
    public void addBuild(AddBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ProjectBuildAddedEvent(
                projectId,
                eventMetaDataFactory.create(),
                command.getBuildId()));
    }

    @EventHandler
    void on(ProjectCreatedEvent event) {
        this.projectId = event.getProjectId();
        this.sequence = 0;
    }

    @EventHandler
    void on(ProjectBuildScheduledEvent event) {
        this.sequence++;
    }
}
