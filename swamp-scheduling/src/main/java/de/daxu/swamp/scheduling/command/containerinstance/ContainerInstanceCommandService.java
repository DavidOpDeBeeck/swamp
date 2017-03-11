package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.build.BuildId;
import de.daxu.swamp.scheduling.command.containerinstance.command.ContainerInstanceCommandFactory;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContainerInstanceCommandService {

    private final CommandGateway gateway;
    private final ContainerInstanceCommandFactory factory;

    @Autowired
    public ContainerInstanceCommandService(CommandGateway gateway,
                                           ContainerInstanceCommandFactory factory) {
        this.gateway = gateway;
        this.factory = factory;
    }

    public void initialize(BuildId buildId, ContainerInstanceId containerInstanceId, ContainerConfiguration configuration, Server server) {
        gateway.send(factory.initializeCommand(containerInstanceId, buildId, configuration, server));
    }

    public void create(ContainerInstanceId containerInstanceId) {
        gateway.send(factory.createCommand(containerInstanceId));
    }

    public void creationSuccess(ContainerInstanceId containerInstanceId, ContainerId containerId) {
        gateway.send(factory.creationSuccessCommand(containerInstanceId, containerId));
    }

    public void creationFailed(ContainerInstanceId containerInstanceId, Set<String> warnings) {
        gateway.send(factory.creationFailedCommand(containerInstanceId, warnings));
    }

    public void start(ContainerInstanceId containerInstanceId) {
        gateway.send(factory.startCommand(containerInstanceId));
    }

    public void stop(ContainerInstanceId containerInstanceId, ContainerInstanceStopReason reason) {
        gateway.send(factory.stopCommand(containerInstanceId, reason));
    }

    public void remove(ContainerInstanceId containerInstanceId, ContainerInstanceRemoveReason reason) {
        gateway.send(factory.removeCommand(containerInstanceId, reason));
    }

    public void startLogging(ContainerInstanceId containerInstanceId) {
        gateway.send(factory.startLoggingCommand(containerInstanceId));
    }

    public void receiveCreationLog(ContainerInstanceId containerInstanceId, String log) {
        gateway.send(factory.receiveCreationLogCommand(containerInstanceId, log));
    }

    public void receiveRunningLog(ContainerInstanceId containerInstanceId, String log) {
        gateway.send(factory.receiveRunningLogCommand(containerInstanceId, log));
    }
}


