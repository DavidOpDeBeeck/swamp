package de.daxu.swamp.docker;

import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.deploy.notifier.ProgressNotifier;
import de.daxu.swamp.deploy.result.DeployResult;
import de.daxu.swamp.docker.command.DockerCommandExecutor;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerContainerClient
        implements ContainerClient {

    private final DockerCommandExecutor executor;
    private final GroupManager groupManager;

    DockerContainerClient(DockerCommandExecutor executor, GroupManager groupManager) {
        this.executor = executor;
        this.groupManager = groupManager;
    }

    @Override
    public DeployResult<ContainerId> create(ContainerConfiguration config, Notifier<String> notifier) {
        GroupId groupId = config.getGroupId();

        DeployResult<Void> createNetwork = executor
                .execute(client -> client.createNetwork(groupId));

        DeployResult<ContainerId> createContainer = executor
                .executeWithResponse(client -> client.createContainer(config, notifier));

        DeployResult<Void> connectToNetwork = executor
                .execute(client -> client.connectContainerToNetwork(groupId, createContainer.get()));

        createContainer.onSuccess(containerId -> groupManager
                .getOrCreate(groupId)
                .addContainer(containerId));

        Set<String> warnings = newHashSet();
        warnings.addAll(createNetwork.warnings());
        warnings.addAll(createContainer.warnings());
        warnings.addAll(connectToNetwork.warnings());

        return DeployResult.result(createContainer.get(), warnings);
    }

    @Override
    public DeployResult<?> start(ContainerId containerId) {
        return executor
                .execute(client -> client.startContainer(containerId));
    }

    @Override
    public DeployResult<?> stop(ContainerId containerId) {
        return executor
                .execute(client -> client.stopContainer(containerId));
    }

    @Override
    public DeployResult<?> remove(ContainerId containerId) {
        return executor
                .execute(client -> client.removeContainer(containerId));
    }

    @Override
    public DeployResult<?> log(ContainerId containerId, ProgressNotifier<String> notifier) {
        return executor
                .execute(client -> client.logContainer(containerId, notifier));
    }

    @Override
    public DeployResult<Boolean> exists(ContainerId containerId) {
        return executor
                .executeWithResponse(client -> client.containerExists(containerId));
    }

    @Override
    public DeployResult<Boolean> isRunning(ContainerId containerId) {
        return executor
                .executeWithResponse(client -> client.isContainerRunning(containerId));
    }

    @Override
    public Server server() {
        return executor.getServer();
    }
}
