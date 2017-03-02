package de.daxu.swamp.docker;

import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.google.common.collect.Sets;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.callback.ProgressCallback;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.client.DeployClient;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.callback.CommandCallback;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.container.DockerContainerConfigurationMapper;
import de.daxu.swamp.workspace.manager.WorkspaceManager;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerContainerClient implements ContainerClient, DeployClient {

    private final Server server;
    private final GroupManager groupManager;

    private final DockerExecutor executor;
    private final DockerContainerConfigurationMapper configurationMapper;

    DockerContainerClient(WorkspaceManager workspaceManager,
                          GroupManager groupManager,
                          DockerClientFactory dockerClientFactory,
                          ContainerResultFactory containerResultFactory,
                          Server server) {
        this.server = server;
        this.groupManager = groupManager;
        this.executor = new DockerExecutor(server, dockerClientFactory, containerResultFactory);
        this.configurationMapper = new DockerContainerConfigurationMapper(executor, workspaceManager);
    }

    @Override
    public ContainerResult create(ContainerConfiguration config, ProgressCallback<String> progressCallback) {
        return executor.exec(warnings -> {
            GroupId groupId = config.getGroupId();

            warnings.addAll(createNetwork(groupId));

            CreateContainerResponse response = configurationMapper
                    .map(config, progressCallback)
                    .exec();

            ContainerId containerId = ContainerId.of(response.getId());

            warnings.addAll(toSet(response.getWarnings()));
            warnings.addAll(connectNetwork(groupId, containerId));

            groupManager.getOrCreate(groupId)
                    .addContainer(containerId);

            return containerId;
        });
    }

    @Override
    public ContainerResult start(ContainerId containerId) {
        return executor.exec(() ->
                executor.execDocker(docker ->
                        docker.startContainerCmd(containerId.value())
                                .exec()), containerId);
    }

    @Override
    public ContainerResult stop(ContainerId containerId) {
        return executor.exec(() ->
                executor.execDocker(docker ->
                        docker.stopContainerCmd(containerId.value())
                                .exec()), containerId);
    }

    @Override
    public ContainerResult remove(ContainerId containerId) {
        return executor.exec(() ->
                executor.execDocker(docker ->
                        docker.removeContainerCmd(containerId.value())
                                .exec()), containerId);
    }

    @Override
    public ContainerResult log(ContainerId containerId, ProgressCallback<String> progressCallback) {
        return executor.exec(() ->
                executor.execDocker(docker ->
                        docker.logContainerCmd(containerId.value())
                                .withStdErr(true)
                                .withStdOut(true)
                                .withFollowStream(true)
                                .exec(onLogReceived(progressCallback))), containerId);
    }

    @Override
    public boolean exists(ContainerId containerId) {
        return executor.exec(() ->
                executor.execDocker(docker ->
                        docker.inspectContainerCmd(containerId.value())
                                .exec()), containerId)
                .isSuccess();
    }

    @Override
    public boolean isRunning(ContainerId containerId) {
        if(!exists(containerId))
            return false;

        String status = executor
                .execDockerWithResponse(docker ->
                        docker.inspectContainerCmd(containerId.value())
                                .exec()
                                .getState()
                                .getStatus());

        return status.equals("created") || status.equals("running");
    }

    @Override
    public Server server() {
        return server;
    }

    private Set<String> createNetwork(GroupId groupId) {
        if(groupManager.exists(groupId)) {
            return newHashSet();
        }
        return executor.execDocker(docker ->
                docker.createNetworkCmd()
                        .withDriver("overlay")
                        .withName(groupId.value())
                        .exec());
    }

    private Set<String> connectNetwork(GroupId groupId, ContainerId containerId) {
        return executor.execDocker(docker ->
                docker.connectToNetworkCmd()
                        .withContainerId(containerId.value())
                        .withNetworkId(groupId.value())
                        .exec()
        );
    }

    private Set<String> toSet(String[] strings) {
        return strings == null ? Sets.newHashSet() : Sets.newHashSet(strings);
    }

    private CommandCallback<LogContainerResultCallback, Frame> onLogReceived(ProgressCallback<String> progressCallback) {
        return new CommandCallback.Builder<LogContainerResultCallback, Frame>()
                .onNext(frame -> progressCallback.onProgress(decodeFrame(frame)))
                .onCompleted(() -> progressCallback.onProgress("Logging finished"))
                .build();
    }

    private String decodeFrame(Frame frame) {
        return frame.toString()
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("STDERR: ", "\n");
    }

}
