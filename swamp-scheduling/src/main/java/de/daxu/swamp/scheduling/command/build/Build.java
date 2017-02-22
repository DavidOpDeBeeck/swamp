package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.scheduling.command.build.command.AddContainerInstanceToBuildCommand;
import de.daxu.swamp.scheduling.command.build.command.InitializeBuildCommand;
import de.daxu.swamp.scheduling.command.build.command.UpdateContainerInstanceStatusFromBuildCommand;
import de.daxu.swamp.scheduling.command.build.event.BuildInitializedEvent;
import de.daxu.swamp.scheduling.command.build.event.ContainerInstanceAddedToBuildEvent;
import de.daxu.swamp.scheduling.command.build.event.ContainerInstanceStatusUpdatedFromBuildEvent;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;
import static de.daxu.swamp.scheduling.command.build.BuildStatus.INPROGRESS;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.INITIALIZED;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.REMOVED;

@SuppressWarnings("unused")
public class Build extends AbstractAnnotatedAggregateRoot<BuildId> {

    @AggregateIdentifier
    private BuildId buildId;
    private BuildStatus status;
    private Map<ContainerInstanceId, ContainerInstanceStatus> containerInstanceStatusMap = newHashMap();

    Build() {
    }

    @CommandHandler
    public Build(InitializeBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new BuildInitializedEvent(
                command.getBuildId(),
                eventMetaDataFactory.create(),
                command.getSequence(),
                command.getProjectName(),
                command.getProjectDescription(),
                command.getContainerInstanceIds()));
    }

    @CommandHandler
    public void AddContainerInstance(AddContainerInstanceToBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ContainerInstanceAddedToBuildEvent(
                buildId,
                eventMetaDataFactory.create(),
                command.getContainerInstanceId()));
    }

    @CommandHandler
    public void UpdateContainerInstanceStatus(UpdateContainerInstanceStatusFromBuildCommand command, EventMetaDataFactory eventMetaDataFactory) {
        boolean allContainersRemoved = allContainersRemoved(command.getContainerInstanceStatus());

        apply(new ContainerInstanceStatusUpdatedFromBuildEvent(
                buildId,
                eventMetaDataFactory.create(),
                command.getContainerInstanceId(),
                command.getContainerInstanceStatus()));

        if (allContainersRemoved) {
            // TODO: BuildStatusUpdate...
        }
    }

    private boolean allContainersRemoved(ContainerInstanceStatus status) {
        return status == REMOVED && containerInstanceStatusMap.values()
                .stream()
                .filter(s -> s == REMOVED)
                .count() == containerInstanceStatusMap.size() - 1;
    }

    @EventHandler
    void on(BuildInitializedEvent event) {
        this.status = INPROGRESS;
        this.buildId = event.getBuildId();
    }

    @EventHandler
    void on(ContainerInstanceAddedToBuildEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), INITIALIZED);
    }

    @EventHandler
    void on(ContainerInstanceStatusUpdatedFromBuildEvent event) {
        this.containerInstanceStatusMap.put(event.getContainerInstanceId(), event.getStatus());
    }
}
