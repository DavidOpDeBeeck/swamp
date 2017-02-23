package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.core.strategy.ServerLocatorStrategy;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.scheduling.command.build.command.BuildCommandFactory;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.query.project.ProjectQueryService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BuildCommandService {

    private static final ServerLocatorStrategy LOCATOR = new FirstInLineStrategy();

    private final CommandGateway commandGateway;
    private final BuildCommandFactory buildCommandFactory;
    private final ProjectQueryService projectQueryService;
    private final ContainerInstanceCommandService containerInstanceCommandFactory;

    @Autowired
    public BuildCommandService(CommandGateway commandGateway,
                               BuildCommandFactory buildCommandFactory,
                               ProjectQueryService projectQueryService,
                               ContainerInstanceCommandService containerInstanceCommandFactory) {
        this.commandGateway = commandGateway;
        this.buildCommandFactory = buildCommandFactory;
        this.projectQueryService = projectQueryService;
        this.containerInstanceCommandFactory = containerInstanceCommandFactory;
    }

    public void initialize(Project project) {
        BuildId buildId = BuildId.random();
        Set<ContainerInstanceId> containerInstanceIds = initializeContainers(buildId, project.getContainers());
        commandGateway.send(
                buildCommandFactory.createInitializeCommand(
                        buildId, project, containerInstanceIds, nextSequence(project)));
        containerInstanceIds.forEach(id -> addContainerInstance(buildId, id));
    }

    public void updateContainerInstanceStatus(BuildId buildId, ContainerInstanceId containerInstanceId, ContainerInstanceStatus status) {
        commandGateway.send(
                buildCommandFactory.createUpdateContainerInstanceStatusCommand(buildId, containerInstanceId, status));
    }

    private void addContainerInstance(BuildId buildId, ContainerInstanceId containerInstanceId) {
        commandGateway.send(
                buildCommandFactory.createAddContainerInstanceCommand(buildId, containerInstanceId));
    }

    private Set<ContainerInstanceId> initializeContainers(BuildId buildId, Set<Container> containers) {
        return containers.stream()
                .map(container -> initializeContainer(buildId, container))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    private Optional<ContainerInstanceId> initializeContainer(BuildId buildId, Container container) {
        Optional<Server> potentialServer = potentialServer(container);

        return potentialServer.map(server -> {
            ContainerConfiguration configuration = containerConfiguration(buildId, container);
            return containerInstanceCommandFactory.initialize(buildId, configuration, server);
        });
    }

    private ContainerConfiguration containerConfiguration(BuildId buildId, Container container) {
        return ContainerConfiguration.of(GroupId.of(buildId.getValue()), container);
    }

    private Optional<Server> potentialServer(Container container) {
        return LOCATOR.locate(container.getPotentialLocations());
    }

    private int nextSequence(Project project) {
        return projectQueryService.getLastBuildSequence(project.getName()) + 1;
    }
}


