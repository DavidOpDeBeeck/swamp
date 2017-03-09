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

    public InitializeContainerInstanceCommand initializeCommand(ContainerInstanceId containerInstanceId,
                                                                BuildId buildId,
                                                                ContainerConfiguration configuration,
                                                                Server server) {
        return new InitializeContainerInstanceCommand(containerInstanceId, buildId, configuration, server);
    }

    public CreateContainerInstanceCommand createCommand(ContainerInstanceId containerInstanceId) {
        return new CreateContainerInstanceCommand(containerInstanceId);
    }

    public StartContainerInstanceCommand startCommand(ContainerInstanceId containerInstanceId) {
        return new StartContainerInstanceCommand(containerInstanceId);
    }

    public StartContainerInstanceLoggingCommand startLoggingCommand(ContainerInstanceId containerInstanceId) {
        return new StartContainerInstanceLoggingCommand(containerInstanceId);
    }

    public StopContainerInstanceCommand stopCommand(ContainerInstanceId containerInstanceId, ContainerInstanceStopReason reason) {
        return new StopContainerInstanceCommand(containerInstanceId, reason);
    }

    public RemoveContainerInstanceCommand removeCommand(ContainerInstanceId containerInstanceId, ContainerInstanceRemoveReason reason) {
        return new RemoveContainerInstanceCommand(containerInstanceId, reason);
    }

    public ReceiveContainerInstanceCreationLogCommand receiveCreationLogCommand(ContainerInstanceId containerInstanceId, String log) {
        return new ReceiveContainerInstanceCreationLogCommand(containerInstanceId, log);
    }

    public ReceiveContainerInstanceRunningLogCommand receiveRunningLogCommand(ContainerInstanceId containerInstanceId, String log) {
        return new ReceiveContainerInstanceRunningLogCommand(containerInstanceId, log);
    }
}
