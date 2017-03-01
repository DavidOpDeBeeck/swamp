package de.daxu.swamp.scheduling.command.containerinstance.command;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceCommandFactory {

    public InitializeContainerInstanceCommand createInitializeCommand( ContainerInstanceId containerInstanceId,
                                                                       BuildId buildId,
                                                                       ContainerConfiguration configuration,
                                                                       Server server ) {
        return new InitializeContainerInstanceCommand( containerInstanceId, buildId, configuration, server );
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

    public StopContainerInstanceCommand createStopCommand( ContainerInstanceId containerInstanceId, ContainerInstanceStopReason reason ) {
        return new StopContainerInstanceCommand( containerInstanceId, reason );
    }

    public RemoveContainerInstanceCommand createRemoveCommand( ContainerInstanceId containerInstanceId, ContainerInstanceRemoveReason reason ) {
        return new RemoveContainerInstanceCommand( containerInstanceId, reason );
    }

    public ReceiveContainerInstanceLogCommand createReceiveLogCommand( ContainerInstanceId containerInstanceId, String log ) {
        return new ReceiveContainerInstanceLogCommand( containerInstanceId, log );
    }
}
