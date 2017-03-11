package de.daxu.swamp.docker;

import de.daxu.swamp.deploy.DeployClient;
import de.daxu.swamp.deploy.DeployResult;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.Group;
import de.daxu.swamp.deploy.group.GroupService;
import de.daxu.swamp.deploy.notifier.DeployNotifier;
import de.daxu.swamp.docker.command.DockerCommandExecutor;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerClient implements DeployClient {

    private final GroupService groupService;
    private final DockerCommandExecutor executor;

    DockerClient(GroupService groupService, DockerCommandExecutor executor) {
        this.groupService = groupService;
        this.executor = executor;
    }

    @Override
    public DeployResult<ContainerId> createContainer(ContainerConfiguration config, DeployNotifier<String> notifier) {
        Group group = Group.withGroupId(config.getGroupId());

        DeployResult<Void> createNetwork = createNetwork(group);
        DeployResult<ContainerId> createContainer = createInternalContainer(config, notifier);

        createContainer.onSuccess(containerId -> groupService
                .getOrCreate(group)
                .addContainer(containerId));

        Set<String> warnings = newHashSet();
        warnings.addAll(createNetwork.warnings());
        warnings.addAll(createContainer.warnings());

        return DeployResult.result(createContainer.get(), warnings);
    }

    private DeployResult<Void> createNetwork(Group group) {
        return executor
                .action(client -> client.createNetwork(group.id()));
    }

    private DeployResult<ContainerId> createInternalContainer(ContainerConfiguration config, DeployNotifier<String> notifier) {
        return executor
                .result(client -> client.createContainer(config, notifier));
    }

    @Override
    public DeployResult<?> startContainer(ContainerId containerId) {
        return executor
                .action(client -> client.startContainer(containerId));
    }

    @Override
    public DeployResult<?> stopContainer(ContainerId containerId) {
        return executor
                .action(client -> client.stopContainer(containerId));
    }

    @Override
    public DeployResult<?> removeContainer(ContainerId containerId) {
        return executor
                .action(client -> client.removeContainer(containerId));
    }

    @Override
    public DeployResult<?> logContainer(ContainerId containerId, DeployNotifier<String> notifier) {
        return executor
                .action(client -> client.logContainer(containerId, notifier));
    }

    @Override
    public DeployResult<Boolean> containerExists(ContainerId containerId) {
        return executor
                .result(client -> client.containerExists(containerId));
    }

    @Override
    public DeployResult<Boolean> isContainerRunning(ContainerId containerId) {
        return executor
                .result(client -> client.isContainerRunning(containerId));
    }
}
