package de.daxu.swamp.scheduling.command.serverinstance;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.event.ContainerInstanceCreatedSucceededEvent;
import de.daxu.swamp.scheduling.command.serverinstance.command.CreateServerInstanceCommand;
import de.daxu.swamp.scheduling.command.serverinstance.event.ServerInstanceCreatedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@SuppressWarnings( "unused" )
public class ServerInstance extends AbstractAnnotatedAggregateRoot<ServerInstanceId> {

    @AggregateIdentifier
    private ServerInstanceId serverInstanceId;
    private Server server;
    private Set<ContainerInstanceId> containerInstances = newHashSet();

    ServerInstance() {
    }

    @CommandHandler
    public ServerInstance( CreateServerInstanceCommand command ) {
        apply( new ServerInstanceCreatedEvent(
                command.getServerInstanceId(),
                command.getServer() )
        );
    }

    @EventHandler
    public void on( ServerInstanceCreatedEvent event ) {
        this.server = event.getServer();
        this.serverInstanceId = event.getServerInstanceId();
    }

    @EventHandler
    public void on( ContainerInstanceCreatedSucceededEvent event ) {
        this.containerInstances.add( event.getContainerInstanceId() );
    }
}
