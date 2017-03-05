package de.daxu.swamp.docker.adapter;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.notifier.Notifier;
import de.daxu.swamp.deploy.notifier.ProgressNotifier;
import de.daxu.swamp.docker.adapter.command.CommandCallbackAdapter;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapter;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapterFactory;

import java.io.File;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerClientAdapter
        implements de.daxu.swamp.docker.client.DockerClient {

    private static final Set<String> VALID_RUNNING_STATUSES = newHashSet("created", "running");

    private final Server server;
    private final DockerClient client;
    private final GroupManager groupManager;
    private final DockerCreateContainerCommandAdapterFactory containerCreateCommandFactory;

    DockerClientAdapter(Server server,
                        DockerClient client,
                        GroupManager groupManager,
                        DockerCreateContainerCommandAdapterFactory containerCreateCommandFactory) {
        this.server = server;
        this.client = client;
        this.groupManager = groupManager;
        this.containerCreateCommandFactory = containerCreateCommandFactory;
    }

    @Override
    public ContainerId createContainer(ContainerConfiguration configuration, Notifier<String> notifier) {
        DockerCreateContainerCommandAdapter command = containerCreateCommandFactory.create(this, configuration, notifier);
        String containerId = command.build(client).exec().getId();
        return ContainerId.of(containerId);
    }

    @Override
    public void startContainer(ContainerId containerId) {
        client.startContainerCmd(containerId.value())
                .exec();
    }

    @Override
    public void stopContainer(ContainerId containerId) {
        client.stopContainerCmd(containerId.value())
                .exec();
    }

    @Override
    public void removeContainer(ContainerId containerId) {
        client.removeContainerCmd(containerId.value())
                .exec();
    }

    @Override
    public void logContainer(ContainerId containerId, ProgressNotifier<String> notifier) {
        client.logContainerCmd(containerId.value())
                .withStdErr(true)
                .withStdOut(true)
                .withFollowStream(true)
                .exec(onLogReceived(notifier));
    }

    @Override
    public void buildContainer(File directory, String tag, Notifier<String> notifier) {
        client.buildImageCmd(directory)
                .withTag(tag)
                .exec(onImageBuild(notifier));
    }

    @Override
    public boolean containerExists(ContainerId containerId) {
        return client.listContainersCmd()
                .exec()
                .stream()
                .anyMatch(container -> container.getId().equals(containerId.value()));
    }

    @Override
    public boolean isContainerRunning(ContainerId containerId) {
        return containerExists(containerId) && VALID_RUNNING_STATUSES
                .contains(client.inspectContainerCmd(containerId.value())
                        .exec()
                        .getState()
                        .getStatus());
    }

    private CommandCallbackAdapter<BuildImageResultCallback, BuildResponseItem> onImageBuild(Notifier<String> notifier) {
        return new CommandCallbackAdapter.Builder<BuildImageResultCallback, BuildResponseItem>()
                .onNext(response -> notifier.onProgress(response.getStream()))
                .onError(notifier::onError)
                .onCompletion(notifier::onCompletion)
                .build();
    }

    // todo: change this to a full Notfifier
    private CommandCallbackAdapter<LogContainerResultCallback, Frame> onLogReceived(ProgressNotifier<String> notifier) {
        return new CommandCallbackAdapter.Builder<LogContainerResultCallback, Frame>()
                .onNext(frame -> notifier.onProgress(decodeFrame(frame)))
                .onCompletion(() -> notifier.onProgress("\nLogging finished"))
                .build();
    }

    private String decodeFrame(Frame frame) {
        return frame.toString()
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("STDERR: ", "\n");
    }

    @Override
    public void createNetwork(GroupId groupId) {
        if (!groupManager.exists(groupId)) {
            client.createNetworkCmd()
                    .withDriver("overlay")
                    .withName(groupId.value())
                    .exec();
        }
    }

    @Override
    public void connectContainerToNetwork(GroupId groupId, ContainerId containerId) {
        client.connectToNetworkCmd()
                .withContainerId(containerId.value())
                .withNetworkId(groupId.value())
                .exec();
    }

    public Server getServer() {
        return server;
    }
}
