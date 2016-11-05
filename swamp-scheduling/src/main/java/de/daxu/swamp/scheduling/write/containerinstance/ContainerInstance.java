package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.command.StartContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceStartedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings( "unused" )
public class ContainerInstance extends AbstractAnnotatedAggregateRoot<String> {

    @AggregateIdentifier
    private ContainerInstanceId containerInstanceId;

    private ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance( CreateContainerInstanceCommand command ) {
        apply( new ContainerInstanceCreatedEvent(
                command.getContainerInstanceId(),
                command.getInternalContainerId(),
                command.getInternalContainerName(),
                command.getDateCreated(),
                command.getServer() )
        );
    }

    @CommandHandler
    public void start( StartContainerInstanceCommand command ) {
        apply( new ContainerInstanceStartedEvent( command.getContainerInstanceId(), command.getDateStarted() ) );
    }

    @EventHandler
    void on( ContainerInstanceCreatedEvent event ) {
        this.containerInstanceId = event.getContainerInstanceId();
    }
}
