package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.command.ContainerInstanceCommandFactory;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContainerInstanceCommandService {

    private final CommandGateway commandGateway;
    private final ContainerInstanceCommandFactory containerInstanceCommandFactory;

    @Autowired
    public ContainerInstanceCommandService( CommandGateway commandGateway,
                                            ContainerInstanceCommandFactory containerInstanceCommandFactory ) {
        this.commandGateway = commandGateway;
        this.containerInstanceCommandFactory = containerInstanceCommandFactory;
    }

    public void initialize(ContainerInstanceId containerInstanceId, BuildId buildId, ContainerConfiguration configuration, Server server ) {
        commandGateway.send( containerInstanceCommandFactory.createInitializeCommand( containerInstanceId, buildId, configuration, server ) );
    }

    public void create( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createCreateCommand( containerInstanceId ) );
    }

    public void start( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartCommand( containerInstanceId ) );
    }

    public void stop( ContainerInstanceId containerInstanceId, ContainerInstanceStopReason reason ) {
        commandGateway.send( containerInstanceCommandFactory.createStopCommand( containerInstanceId, reason ) );
    }

    public void remove( ContainerInstanceId containerInstanceId, ContainerInstanceRemoveReason reason ) {
        commandGateway.send( containerInstanceCommandFactory.createRemoveCommand( containerInstanceId, reason ) );
    }

    public void startLogging( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartLoggingCommand( containerInstanceId ) );
    }

    public void receiveLog( ContainerInstanceId containerInstanceId, String log ) {
        commandGateway.send( containerInstanceCommandFactory.createReceiveLogCommand( containerInstanceId, log ) );
    }
}


