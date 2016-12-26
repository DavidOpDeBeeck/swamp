package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceCommandFactory {

    public InitializeContainerInstanceCommand createInitializeCommand( ContainerInstanceId containerInstanceId, ContainerConfiguration configuration, Server server ) {
        return new InitializeContainerInstanceCommand( containerInstanceId, configuration, server );
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
