package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.scheduling.command.build.command.InitializeBuildCommand;
import de.daxu.swamp.scheduling.command.build.event.BuildInitializedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

@SuppressWarnings("unused")
public class Build extends AbstractAnnotatedAggregateRoot<BuildId> {

    @AggregateIdentifier
    private BuildId buildId;

    Build() {
    }

    @CommandHandler
    public Build(InitializeBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        Project project = command.getProject();
        Map<ContainerInstanceId, Container> containers = project.getContainers()
                .stream()
                .collect(toMap(container -> ContainerInstanceId.random(), container -> container));

        apply(new BuildInitializedEvent(
                command.getBuildId(),
                eventMetaDataFactory.create(),
                0,
                project.getName(),
                project.getDescription(),
                containers));
    }

    @EventHandler
    void on(BuildInitializedEvent event) {
        this.buildId = event.getBuildId();
    }
}
