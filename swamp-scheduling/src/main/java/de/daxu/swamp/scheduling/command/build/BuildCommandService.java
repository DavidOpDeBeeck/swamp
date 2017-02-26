package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.build.command.BuildCommandFactory;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BuildCommandService {

    private final CommandGateway gateway;
    private final BuildCommandFactory factory;

    @Autowired
    public BuildCommandService(CommandGateway gateway,
                               BuildCommandFactory factory) {
        this.gateway = gateway;
        this.factory = factory;
    }

    void createBuild(ProjectId projectId, int sequence, Set<Container> containers) {
        gateway.send(factory.createBuildCommand(BuildId.random(), projectId, sequence, containers));
    }

    void addContainerInstance(BuildId buildId, ContainerInstanceId containerInstanceId) {
        gateway.send(factory.addContainerInstanceCommand(buildId, containerInstanceId));
    }

    void updateContainerInstanceStatus(BuildId buildId, ContainerInstanceId containerInstanceId, ContainerInstanceStatus status) {
        gateway.send(factory.updateContainerInstanceStatusCommand(buildId, containerInstanceId, status));
    }
}


