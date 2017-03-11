package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.async.AsyncRunner;
import de.daxu.swamp.common.cqrs.EventMetaDataFactory;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.AsyncDeployExecutor;
import de.daxu.swamp.deploy.DeployClientManager;
import de.daxu.swamp.deploy.DeployResult;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.notifier.BufferedProgressNotifier;
import de.daxu.swamp.deploy.notifier.DeployNotifier;
import de.daxu.swamp.deploy.notifier.DeployNotifier.ProgressNotifier;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.command.*;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static de.daxu.swamp.deploy.notifier.BufferedProgressNotifier.Builder.aBufferedProgressNotifier;
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

    @Autowired
    private AsyncRunner asyncRunner;
    @Autowired
    private AsyncDeployExecutor asyncDeploy;
    @Autowired
    private DeployClientManager clientManager;
    @Autowired
    private EventMetaDataFactory eventMetaDataFactory;
    @Autowired
    private ContainerInstanceCommandService service;

    ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance(InitializeContainerInstanceCommand command,
                             EventMetaDataFactory eventMetaDataFactory) {
        apply(new ContainerInstanceInitializedEvent(
                command.getContainerInstanceId(),
                eventMetaDataFactory.create(),
                command.getBuildId(),
                command.getServer(),
                command.getConfiguration()));
    }

    @CommandHandler
    public void create(CreateContainerInstanceCommand command) {
        validateStatusChange(CREATION);

        asyncDeploy
                .action(this::createContainer)
                .onSuccess(this::creationSuccess)
                .onFail(this::creationFailed)
                .execute();

        apply(new ContainerInstanceCreationStartedEvent(containerInstanceId, buildId, eventMetaDataFactory.create()));
    }

    @CommandHandler
    public void creationSuccess(CreationSuccessContainerInstanceCommand command) {
        validateStatusChange(CREATED);

        apply(new ContainerInstanceCreatedSucceededEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId));
    }

    @CommandHandler
    public void creationFailed(CreationFailedContainerInstanceCommand command) {
        validateStatusChange(CREATED);

        apply(new ContainerInstanceCreatedFailedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                command.getWarnings()));
    }

    @CommandHandler
    public void start(StartContainerInstanceCommand command) {
        validateStatusChange(STARTED);

        DeployResult<?> result = clientManager
                .createClient(server)
                .startContainer(containerId);

        result.onSuccess(() ->
                apply(new ContainerInstanceStartedSucceededEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId)));

        result.onFail(warnings ->
                apply(new ContainerInstanceStartedFailedEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        warnings)));
    }

    @CommandHandler
    public void stop(StopContainerInstanceCommand command) {
        validateStatusChange(STOPPED);

        DeployResult<?> result = clientManager
                .createClient(server)
                .stopContainer(containerId);

        result.onSuccess(() ->
                apply(new ContainerInstanceStoppedSucceededEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        command.getReason()))
        );

        result.onFail(warnings ->
                apply(new ContainerInstanceStoppedFailedEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        warnings,
                        command.getReason()))
        );
    }

    @CommandHandler
    public void remove(RemoveContainerInstanceCommand command) {
        validateStatusChange(REMOVED);

        DeployResult<?> result = clientManager
                .createClient(server)
                .removeContainer(containerId);

        result.onSuccess(() ->
                apply(new ContainerInstanceRemovedSucceededEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        command.getReason())));


        result.onFail(warnings ->
                apply(new ContainerInstanceRemovedFailedEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        warnings,
                        command.getReason())));
    }

    @CommandHandler
    public void startRunningLogging(StartContainerInstanceRunningLoggingCommand command) {
        validateStatus(STARTED);

        DeployResult<?> result = clientManager
                .createClient(server)
                .logContainer(containerId, runningNotifier());

        result.onSuccess(() ->
                apply(new ContainerInstanceRunningLoggingStartedSucceededEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId)));

        result.onFail(warnings ->
                apply(new ContainerInstanceRunningLoggingStartedFailedEvent(
                        containerInstanceId,
                        buildId,
                        eventMetaDataFactory.create(),
                        containerId,
                        warnings)));
    }

    @CommandHandler
    public void receiveCreationLog(ReceiveContainerInstanceCreationLogCommand command) {
        apply(new ContainerInstanceCreationLogReceivedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                command.getLog()));
    }

    @CommandHandler
    public void receiveRunningLog(ReceiveContainerInstanceRunningLogCommand command) {
        apply(new ContainerInstanceRunningLogReceivedEvent(
                containerInstanceId,
                buildId,
                eventMetaDataFactory.create(),
                containerId,
                command.getLog()));
    }

    private DeployResult<ContainerId> createContainer() {
        return clientManager
                .createClient(server)
                .createContainer(configuration, creationNotifier());
    }

    private void creationFailed(Set<String> warnings) {
        asyncRunner.forRunnable(() ->
                service.creationFailed(containerInstanceId, warnings));
    }

    private void creationSuccess(ContainerId containerId) {
        asyncRunner.forRunnable(() ->
                service.creationSuccess(containerInstanceId, containerId));
    }

    private DeployNotifier<String> creationNotifier() {
        return DeployNotifier.forProgress(
                defaultProgressNotifier(log ->
                        asyncRunner.forRunnable(() ->
                                service.receiveCreationLog(containerInstanceId, log)))
        );
    }

    private DeployNotifier<String> runningNotifier() {
        return DeployNotifier.forProgress(
                defaultProgressNotifier(log ->
                        asyncRunner.forRunnable(() ->
                                service.receiveRunningLog(containerInstanceId, log)))
        );
    }

    private BufferedProgressNotifier defaultProgressNotifier(ProgressNotifier<String> delegate) {
        return aBufferedProgressNotifier()
                .withBufferLimit(200)
                .withBufferWaitTime(5000)
                .withDelegate(delegate)
                .build();
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

    @EventSourcingHandler
    void on(ContainerInstanceInitializedEvent event) {
        this.status = INITIALIZED;
        this.buildId = event.getBuildId();
        this.server = event.getServer();
        this.configuration = event.getConfiguration();
        this.containerInstanceId = event.getContainerInstanceId();
    }

    @EventSourcingHandler
    void on(ContainerInstanceCreationStartedEvent event) {
        this.status = CREATION;
    }
    @EventSourcingHandler
    void on(ContainerInstanceCreatedSucceededEvent event) {
        this.status = CREATED;
        this.containerId = event.getContainerId();
    }

    @EventSourcingHandler
    void on(ContainerInstanceStartedEvent event) {
        this.status = STARTED;
    }

    @EventSourcingHandler
    void on(ContainerInstanceStoppedEvent event) {
        this.status = STOPPED;
    }

    @EventSourcingHandler
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
