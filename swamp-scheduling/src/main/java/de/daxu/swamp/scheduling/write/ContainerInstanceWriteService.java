package de.daxu.swamp.scheduling.write;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.command.ContainerInstanceCommandFactory;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static de.daxu.swamp.deploy.configuration.ContainerConfiguration.Builder.aContainerConfiguration;

@Service
public class ContainerInstanceWriteService {

    private final CommandGateway commandGateway;
    private final ContainerInstanceCommandFactory containerInstanceCommandFactory;

    @Autowired
    public ContainerInstanceWriteService( CommandGateway commandGateway,
                                          ContainerInstanceCommandFactory containerInstanceCommandFactory ) {
        this.commandGateway = commandGateway;
        this.containerInstanceCommandFactory = containerInstanceCommandFactory;
    }

    public void schedule( Container container, Server server ) {
        ContainerConfiguration configuration = aContainerConfiguration()
                .withPortMappings( container.getPortMappings() )
                .withEnvironmentVariables( container.getEnvironmentVariables() )
                .withRunConfiguration( container.getRunConfiguration() ).build();
        initialize( server, configuration );
    }

    public void initialize( Server server, ContainerConfiguration configuration ) {
        commandGateway.send( containerInstanceCommandFactory.createInitializeCommand( server, configuration ) );
    }

    public void create( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createCreateCommand( containerInstanceId ) );
    }

    public void start( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartCommand( containerInstanceId ) );
    }

    public void stop( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStopCommand( containerInstanceId ) );
    }

    public void remove( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createRemoveCommand( containerInstanceId ) );
    }

    public void startLogging( ContainerInstanceId containerInstanceId ) {
        commandGateway.send( containerInstanceCommandFactory.createStartLoggingCommand( containerInstanceId ) );
    }

    public void receiveLog( ContainerInstanceId containerInstanceId, String log ) {
        commandGateway.send( containerInstanceCommandFactory.createReceiveLogCommand( containerInstanceId, log ) );
    }
}


