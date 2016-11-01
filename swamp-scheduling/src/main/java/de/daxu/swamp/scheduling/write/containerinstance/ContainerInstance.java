package de.daxu.swamp.scheduling.write.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.command.CreateContainerInstance;
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

    ContainerInstance() {
    }

    @CommandHandler
    public ContainerInstance( CreateContainerInstance command ) {
        apply( new ContainerInstanceCreated( command.getContainerInstanceId(), command.getName() ) );
    }

    @EventHandler
    void on( ContainerInstanceCreated event ) {
        this.containerInstanceId = event.getContainerInstanceId();
    }

}
