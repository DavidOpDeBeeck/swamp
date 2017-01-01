package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.scheduling.command.containerinstance.command.*;
import de.daxu.swamp.scheduling.command.containerinstance.event.*;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.time.LocalDateTime;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;

@SuppressWarnings( "unused" )
public class ContainerInstance extends AbstractAnnotatedAggregateRoot<ContainerInstanceId> {

    @AggregateIdentifier
    private ContainerInstanceId containerInstanceId;

    private Server server;
    private ContainerId containerId;
    private ContainerConfiguration configuration;
    private ContainerInstanceStatus status;

    ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance( InitializeContainerInstanceCommand command ) {
        apply( new ContainerInstanceInitializedEvent(
                command.getContainerInstanceId(),
                command.getServer(),
                command.getConfiguration(), LocalDateTime.now() ) );
    }

    @CommandHandler
    public void create( CreateContainerInstanceCommand command, DeployFacade deployFacade ) {
        validateStatusChange( CREATED );

        ContainerResult response = deployFacade
                .containerClient( server )
                .create( configuration );

        apply( new ContainerInstanceCreatedEvent(
                containerInstanceId,
                response.getContainerId(), LocalDateTime.now() ) );
    }

    @CommandHandler
    public void start( StartContainerInstanceCommand command, DeployFacade deployFacade ) {
        validateStatusChange( STARTED );

        deployFacade
                .containerClient( server )
                .start( containerId );

        apply( new ContainerInstanceStartedEvent( containerInstanceId, LocalDateTime.now() ) );
    }

    @CommandHandler
    public void stop( StopContainerInstanceCommand command, DeployFacade deployFacade ) {
        validateStatusChange( STOPPED );

        deployFacade
                .containerClient( server )
                .stop( containerId );

        apply( new ContainerInstanceStoppedEvent( containerInstanceId, LocalDateTime.now() ) );
    }

    @CommandHandler
    public void remove( RemoveContainerInstanceCommand command, DeployFacade deployFacade ) {
        validateStatusChange( REMOVED );

        deployFacade
                .containerClient( server )
                .remove( containerId );

        apply( new ContainerInstanceRemovedEvent( containerInstanceId, LocalDateTime.now() ) );
    }

    @CommandHandler
    public void startLogging( StartContainerInstanceLoggingCommand command,
                              DeployFacade deployFacade,
                              ContainerInstanceCommandService service ) {
        validateStatus( STARTED );

        deployFacade
                .containerClient( server )
                .log( containerId, ( log ) -> service.receiveLog( containerInstanceId, log ) );

        apply( new ContainerInstanceLoggingStartedEvent( containerInstanceId, LocalDateTime.now() ) );
    }

    @CommandHandler
    public void receiveLog( ReceiveContainerInstanceLogCommand command ) {
        validateStatus( STARTED );
        apply( new ContainerInstanceLogReceivedEvent( containerInstanceId, command.getLog(), LocalDateTime.now() ) );
    }

    private void validateStatusChange( ContainerInstanceStatus nextStatus ) {
        if( !isValidPreviousStatus( nextStatus, status ) ) {
            throw new InvalidContainerStatusChangeException( status, nextStatus );
        }
    }

    private void validateStatus( ContainerInstanceStatus statusToBe ) {
        if( this.status != statusToBe ) {
            throw new InvalidContainerStatusException( status, statusToBe );
        }
    }

    @EventHandler
    void on( ContainerInstanceInitializedEvent event ) {
        this.status = INITIALIZED;
        this.server = event.getServer();
        this.configuration = event.getConfiguration();
        this.containerInstanceId = event.getContainerInstanceId();
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        this.status = CREATED;
        this.containerId = event.getContainerId();
    }

    @EventHandler
    void on( ContainerInstanceStartedEvent event ) {
        this.status = STARTED;
    }

    public ContainerInstanceId getContainerInstanceId() {
        return containerInstanceId;
    }

    public ContainerInstanceStatus getStatus() {
        return status;
    }
}
