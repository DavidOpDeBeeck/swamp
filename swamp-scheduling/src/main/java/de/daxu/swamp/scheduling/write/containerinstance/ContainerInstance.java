package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstanceCommand;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreated;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings( "unused" )
public class ContainerInstance extends AbstractAnnotatedAggregateRoot<String> {

    @AggregateIdentifier
    private ContainerInstanceId containerInstanceId;
    private String name;

    private ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance( CreateContainerInstanceCommand command ) {
        apply( new ContainerInstanceCreated( command.getAggregateId(), command.getName() ) );
    }

    @EventHandler
    void on( ContainerInstanceCreated event ) {
        this.containerInstanceId = event.getAggregateId();
    }
}
