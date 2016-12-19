package de.daxu.swamp.scheduling.write.containerinstance.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceCommandFactory {

    public InitializeContainerInstanceCommand createInitializeCommand( Server server, ContainerConfiguration configuration ) {
        return new InitializeContainerInstanceCommand( ContainerInstanceId.random(), server, configuration );
    }

    public CreateContainerInstanceCommand createCreateCommand( ContainerInstanceId containerInstanceId ) {
        return new CreateContainerInstanceCommand( containerInstanceId );
    }

    public StartContainerInstanceCommand createStartCommand( ContainerInstanceId containerInstanceId ) {
        return new StartContainerInstanceCommand( containerInstanceId );
    }

    public StartContainerInstanceLoggingCommand createStartLoggingCommand( ContainerInstanceId containerInstanceId ) {
        return new StartContainerInstanceLoggingCommand( containerInstanceId );
    }

    public StopContainerInstanceCommand createStopCommand( ContainerInstanceId containerInstanceId ) {
        return new StopContainerInstanceCommand( containerInstanceId );
    }

    public RemoveContainerInstanceCommand createRemoveCommand( ContainerInstanceId containerInstanceId ) {
        return new RemoveContainerInstanceCommand( containerInstanceId );
    }

    public ReceiveContainerInstanceLogCommand createReceiveLogCommand( ContainerInstanceId containerInstanceId, String log ) {
        return new ReceiveContainerInstanceLogCommand( containerInstanceId, log );
    }
}
