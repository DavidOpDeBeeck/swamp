package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.cqrs.EventMetaData;
import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.scheduling.command.build.command.CreateBuildCommand;
import de.daxu.swamp.scheduling.command.build.command.UpdateContainerInstanceStatusCommand;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceScheduledEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildContainerInstanceStatusChangedEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildCreatedEvent;
import de.daxu.swamp.scheduling.command.build.event.BuildFinishedEvent;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

import static com.google.common.collect.Maps.newHashMap;
import static de.daxu.swamp.scheduling.command.build.BuildStatus.INPROGRESS;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.INITIALIZED;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.REMOVED;

@SuppressWarnings("unused")
public class Build extends AbstractAnnotatedAggregateRoot<BuildId> {

    @AggregateIdentifier
    private BuildId buildId;
    private ProjectId projectId;
    private BuildStatus status;
    private Map<ContainerInstanceId, ContainerInstanceStatus> containerInstanceStatusMap = newHashMap();

    @Autowired
    private EventMetaDataFactory eventMetaDataFactory;

    Build() {
    }

    @CommandHandler
    public Build(CreateBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new BuildCreatedEvent(
                command.getBuildId(),
                command.getProjectId(),
                eventMetaDataFactory.create(),
                command.getSequence()));

        command.getContainers()
                .forEach(container ->
                        scheduleContainerInstance(command.getBuildId(), container, eventMetaDataFactory.create()));
    }

    @CommandHandler
    public void updateContainerInstanceStatus(UpdateContainerInstanceStatusCommand command) {
        validateStatus(INPROGRESS);

        boolean allContainersRemoved = allContainersRemoved(command.getContainerInstanceStatus());

        apply(new BuildContainerInstanceStatusChangedEvent(
                buildId,
                projectId,
                eventMetaDataFactory.create(),
                command.getContainerInstanceId(),
                command.getContainerInstanceStatus()));

        if (allContainersRemoved) {
            apply(new BuildFinishedEvent(buildId, projectId, eventMetaDataFactory.create()));
        }
    }

    private void scheduleContainerInstance(BuildId buildId, Container container, EventMetaData eventMetaData) {
        Optional<Server> potentialServer = potentialServer(container);
        potentialServer.ifPresent(server ->
                applyScheduledEvent(buildId, container, eventMetaData, server));
    }

    private void applyScheduledEvent(BuildId buildId, Container container, EventMetaData eventMetaData, Server server) {
        apply(new BuildContainerInstanceScheduledEvent(
                buildId,
                projectId,
                eventMetaData,
                ContainerInstanceId.random(),
                containerConfiguration(buildId, container),
                server));
    }

    private ContainerConfiguration containerConfiguration(BuildId buildId, Container container) {
        return ContainerConfiguration.of(GroupId.of(buildId.value()), container);
    }

    private Optional<Server> potentialServer(Container container) {
        return new FirstInLineStrategy().locate(container.getPotentialLocations());
    }

    private boolean allContainersRemoved(ContainerInstanceStatus status) {
        return status == REMOVED && containerInstanceStatusMap.values()
                .stream()
                .filter(s -> s == REMOVED)
                .count() == containerInstanceStatusMap.size() - 1;
    }

    private void validateStatus(BuildStatus statusToBe) {
        if (this.status != statusToBe) {
            throw new InvalidBuildStatusException(status, statusToBe);
        }
    }

    @EventSourcingHandler
    void on(BuildCreatedEvent event) {
        this.status = INPROGRESS;
        this.buildId = event.getBuildId();
        this.projectId = event.getProjectId();
    }

    @EventSourcingHandler
    void on(BuildContainerInstanceScheduledEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), INITIALIZED);
    }

    @EventSourcingHandler
    void on(BuildContainerInstanceStatusChangedEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), event.getContainerInstanceStatus());
    }
}
