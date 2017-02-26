package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.scheduling.command.build.command.AddContainerInstanceCommand;
import de.daxu.swamp.scheduling.command.build.command.CreateBuildCommand;
import de.daxu.swamp.scheduling.command.build.command.UpdateContainerInstanceStatusCommand;
import de.daxu.swamp.scheduling.command.build.event.*;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

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
                        scheduleContainerInstance(command.getBuildId(), container, eventMetaDataFactory));
    }

    private void scheduleContainerInstance(BuildId buildId, Container container, EventMetaDataFactory eventMetaDataFactory) {
        Optional<Server> potentialServer = potentialServer(container);
        potentialServer.ifPresent(server ->
                apply(new BuildContainerInstanceScheduledEvent(
                        buildId, projectId, eventMetaDataFactory.create(), containerConfiguration(buildId, container), server))
        );
    }

    private ContainerConfiguration containerConfiguration(BuildId buildId, Container container) {
        return ContainerConfiguration.of(GroupId.of(buildId.value()), container);
    }

    private Optional<Server> potentialServer(Container container) {
        return new FirstInLineStrategy().locate(container.getPotentialLocations());
    }

    @CommandHandler
    public void addContainerInstance(AddContainerInstanceCommand command, EventMetaDataFactory eventMetaDataFactory) {
        validateStatus(INPROGRESS);

        apply(new BuildContainerInstanceAddedEvent(
                buildId,
                projectId,
                eventMetaDataFactory.create(),
                command.getContainerInstanceId()));
    }

    @CommandHandler
    public void updateContainerInstanceStatus(UpdateContainerInstanceStatusCommand command, EventMetaDataFactory eventMetaDataFactory) {
        validateStatus(INPROGRESS);

        boolean allContainersRemoved = allContainersRemoved(command.getContainerInstanceStatus());

        apply(new BuildContainerInstanceStatusChangedEvent(
                buildId,
                projectId,
                eventMetaDataFactory.create(),
                command.getContainerInstanceId(),
                command.getContainerInstanceStatus()));

        if(allContainersRemoved) {
            apply(new BuildFinishedEvent(buildId, projectId, eventMetaDataFactory.create()));
        }
    }

    private boolean allContainersRemoved(ContainerInstanceStatus status) {
        return status == REMOVED && containerInstanceStatusMap.values()
                .stream()
                .filter(s -> s == REMOVED)
                .count() == containerInstanceStatusMap.size() - 1;
    }

    private void validateStatus(BuildStatus statusToBe) {
        if(this.status != statusToBe) {
            throw new InvalidBuildStatusException(status, statusToBe);
        }
    }

    @EventHandler
    void on(BuildCreatedEvent event) {
        this.status = INPROGRESS;
        this.buildId = event.getBuildId();
        this.projectId = event.getProjectId();
    }

    @EventHandler
    void on(BuildContainerInstanceAddedEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), INITIALIZED);
    }

    @EventHandler
    void on(BuildContainerInstanceStatusChangedEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), event.getContainerInstanceStatus());
    }
}
