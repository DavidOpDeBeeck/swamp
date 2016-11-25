package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.command.ScheduleContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.command.StartContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceScheduledEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceStartedEvent;
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
