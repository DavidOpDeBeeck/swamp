package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.command.*;
import de.daxu.swamp.scheduling.write.containerinstance.event.*;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings( "unused" )
public class ContainerInstance extends AbstractAnnotatedAggregateRoot<String> {

    @AggregateIdentifier
    private ContainerInstanceId containerInstanceId;

    private Server server;
    private ContainerInstanceStatus status;

    ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance( ScheduleContainerInstanceCommand command ) {
        Container container = command.getContainer();
        apply( new ContainerInstanceScheduledEvent(
                        command.getContainerInstanceId(),
                        container.getName(),
                        container.getRunConfiguration(),
                        container.getPortMappings(),
                        container.getEnvironmentVariables(),
                        command.getServer(),
                        command.getDateScheduled()
                )
        );
    }

    @CommandHandler
    public void create( CreateContainerInstanceCommand command ) {
        apply( new ContainerInstanceCreatedEvent(
                command.getContainerInstanceId(),
                command.getInternalContainerId(),
                command.getInternalContainerName(),
                command.getDateCreated() )
        );
    }

    @CommandHandler
    public void start( StartContainerInstanceCommand command ) {
        apply( new ContainerInstanceStartedEvent(
                command.getContainerInstanceId(),
                command.getDateStarted() )
        );
    }

    @CommandHandler
    public void stop( StopContainerInstanceCommand command ) {
        apply( new ContainerInstanceStoppedEvent(
                command.getContainerInstanceId(),
                command.getDateStopped() )
        );
    }

    @CommandHandler
    public void remove( RemoveContainerInstanceCommand command ) {
        apply( new ContainerInstanceRemovedEvent(
                command.getContainerInstanceId(),
                command.getDateRemoved() )
        );
    }

    @CommandHandler
    public void startLogging( StartContainerInstanceLoggingCommand command ) {
        apply( new ContainerInstanceLoggingStartedEvent(
                command.getContainerInstanceId(),
                command.getDateLoggingStarted() )
        );
    }

    @CommandHandler
    public void receiveLog( ReceiveContainerInstanceLogCommand command ) {
        apply( new ContainerInstanceLogReceivedEvent(
                command.getContainerInstanceId(),
                command.getLog(),
                command.getDateLogReceived() )
        );
    }

    @EventHandler
    void on( ContainerInstanceScheduledEvent event ) {
        this.server = event.getServer();
        this.status = ContainerInstanceStatus.SCHEDULED;
        this.containerInstanceId = event.getContainerInstanceId();
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        this.status = ContainerInstanceStatus.CREATED;
    }

    @EventHandler
    void on( ContainerInstanceStartedEvent event ) {
        this.status = ContainerInstanceStatus.STARTED;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public Server getServer() {
        return server;
    }

    public ContainerInstanceStatus getStatus() {
        return status;
    }
}
