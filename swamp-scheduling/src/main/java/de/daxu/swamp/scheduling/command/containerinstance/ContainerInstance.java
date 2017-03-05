package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.deploy.result.DeployResult;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.command.*;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;

@SuppressWarnings("unused")
public class ContainerInstance extends AbstractAnnotatedAggregateRoot<ContainerInstanceId> {

    private final Logger logger = LoggerFactory.getLogger(ContainerInstance.class);

    @AggregateIdentifier
    private ContainerInstanceId containerInstanceId;

    private BuildId buildId;
    private Server server;
    private ContainerId containerId;
    private ContainerConfiguration configuration;
    private ContainerInstanceStatus status;

    ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance(InitializeContainerInstanceCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ContainerInstanceInitializedEvent(
                command.getContainerInstanceId(),
                eventMetaDataFactory.create(),
                command.getBuildId(),
                command.getServer(),
                command.getConfiguration()));
    }

    @CommandHandler
    public void create(CreateContainerInstanceCommand command, DeployFacade deployFacade, EventMetaDataFactory eventMetaDataFactory) {
        validateStatusChange(CREATED);

        DeployResult<ContainerId> result = deployFacade
                .containerClient(server)
                .create(configuration, createNotifier(eventMetaDataFactory));

        result.onSuccess(containerId -> apply(new ContainerInstanceCreatedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId)));

        result.onFailed(() -> apply(new ContainerInstanceCreatedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                null,
                result.warnings())));
    }

    private Notifier<String> createNotifier(EventMetaDataFactory eventMetaDataFactory) {
        return new Notifier.Builder<String>()
                .withNextNotifier(payload -> apply(creationLogEvent(eventMetaDataFactory, payload)))
                .build();
    }

    private ContainerInstanceCreationLogReceivedEvent creationLogEvent(EventMetaDataFactory eventMetaDataFactory,
                                                                       String payload) {
        return new ContainerInstanceCreationLogReceivedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                payload);
    }

    @CommandHandler
    public void start(StartContainerInstanceCommand command, DeployFacade deployFacade, EventMetaDataFactory eventMetaDataFactory) {
        validateStatusChange(STARTED);

        DeployResult<?> result = deployFacade
                .containerClient(server)
                .start(containerId);

        result.onSuccess((o) -> apply(new ContainerInstanceStartedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId)));

        result.onFailed(() -> apply(new ContainerInstanceStartedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                result.warnings())));
    }

    @CommandHandler
    public void stop(StopContainerInstanceCommand command, DeployFacade deployFacade, EventMetaDataFactory eventMetaDataFactory) {
        validateStatusChange(STOPPED);

        DeployResult<?> result = deployFacade
                .containerClient(server)
                .stop(containerId);


        result.onSuccess((o) -> apply(new ContainerInstanceStoppedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                command.getReason()
        )));

        result.onFailed(() -> apply(new ContainerInstanceStoppedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                result.warnings(),
                command.getReason())));
    }

    @CommandHandler
    public void remove(RemoveContainerInstanceCommand command, DeployFacade deployFacade, EventMetaDataFactory eventMetaDataFactory) {
        validateStatusChange(REMOVED);

        DeployResult<?> result = deployFacade
                .containerClient(server)
                .remove(containerId);

        result.onSuccess((o) -> apply(new ContainerInstanceRemovedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                command.getReason())));

        result.onFailed(() -> apply(new ContainerInstanceRemovedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                result.warnings(),
                command.getReason())));
    }

    @CommandHandler
    public void startLogging(StartContainerInstanceLoggingCommand command,
                             DeployFacade deployFacade,
                             ContainerInstanceCommandService service,
                             EventMetaDataFactory eventMetaDataFactory) {
        validateStatus(STARTED);

        DeployResult<?> result = deployFacade
                .containerClient(server)
                .log(containerId, (log) -> service.receiveLog(containerInstanceId, log));

        result.onSuccess((o) -> apply(new ContainerInstanceLoggingStartedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId
        )));

        result.onFailed(() -> apply(new ContainerInstanceLoggingStartedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                result.warnings())));
    }

    @CommandHandler
    public void receiveLog(ReceiveContainerInstanceLogCommand command, EventMetaDataFactory eventMetaDataFactory) {
        apply(new ContainerInstanceRunningLogReceivedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                command.getLog()));
    }

    private void validateStatusChange(ContainerInstanceStatus nextStatus) {
        if (!isValidPreviousStatus(nextStatus, status)) {
            throw new InvalidContainerStatusChangeException(status, nextStatus);
        }
    }

    private void validateStatus(ContainerInstanceStatus statusToBe) {
        if (this.status != statusToBe) {
            throw new InvalidContainerStatusException(status, statusToBe);
        }
    }

    @EventHandler
    void on(ContainerInstanceInitializedEvent event) {
        this.status = INITIALIZED;
        this.buildId = event.getBuildId();
        this.server = event.getServer();
        this.configuration = event.getConfiguration();
        this.containerInstanceId = event.getContainerInstanceId();
    }

    @EventHandler
    void on(ContainerInstanceCreatedSucceededEvent event) {
        this.status = CREATED;
        this.containerId = event.getContainerId();
    }

    @EventHandler
    void on(ContainerInstanceStartedEvent event) {
        this.status = STARTED;
    }

    @EventHandler
    void on(ContainerInstanceStoppedEvent event) {
        this.status = STOPPED;
    }

    @EventHandler
    void on(ContainerInstanceRemovedEvent event) {
        this.status = REMOVED;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public ContainerInstanceStatus getStatus() {
        return status;
    }
}
