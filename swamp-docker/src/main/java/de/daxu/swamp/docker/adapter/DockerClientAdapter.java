package de.daxu.swamp.docker.adapter;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.common.time.Dates;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupService;
import de.daxu.swamp.deploy.notifier.DeployNotifier;
import de.daxu.swamp.docker.adapter.command.DockerCommandCallback;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapter;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapterFactory;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;

import java.io.File;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerClientAdapter
        implements DockerBehaviour {

    private static final Set<String> VALID_RUNNING_STATUSES = newHashSet("created", "running");

    private final Server server;
    private final DockerClient client;
    private final GroupService groupService;
    private final DockerCreateContainerCommandAdapterFactory containerCreateCommandFactory;

    DockerClientAdapter(Server server,
                        DockerClient client,
                        GroupService groupService,
                        DockerCreateContainerCommandAdapterFactory containerCreateCommandFactory) {
        this.server = server;
        this.client = client;
        this.groupService = groupService;
        this.containerCreateCommandFactory = containerCreateCommandFactory;
    }

    @Override
    public ContainerId createContainer(ContainerConfiguration configuration, DeployNotifier<String> notifier) {
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
    public void logContainer(ContainerId containerId, DeployNotifier<String> notifier) {
        client.logContainerCmd(containerId.value())
                .withStdErr(true)
                .withStdOut(true)
                .withFollowStream(true)
                .exec(onLogReceived(notifier));
    }

    @Override
    public void buildContainer(File directory, String tag, DeployNotifier<String> notifier) {
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

    private DockerCommandCallback<BuildImageResultCallback, BuildResponseItem> onImageBuild(DeployNotifier<String> notifier) {
        return new DockerCommandCallback.Builder<BuildImageResultCallback, BuildResponseItem>()
                .onNext(response -> notifier.triggerProgress(response.getStream()))
                .onError(notifier::triggerError)
                .onComplete(notifier::triggerCompletion)
                .build();
    }

    private DockerCommandCallback<LogContainerResultCallback, Frame> onLogReceived(DeployNotifier<String> notifier) {
        return new DockerCommandCallback.Builder<LogContainerResultCallback, Frame>()
                .onNext(frame -> notifier.triggerProgress(decodeFrame(frame)))
                .onComplete(() -> notifier.triggerProgress(String.format("\nLogging completed at %s", Dates.now().toString())))
                .build();
    }

    private String decodeFrame(Frame frame) {
        return frame.toString()
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("STDOUT: ", "\n")
                .replaceAll("null: ", "");
    }

    @Override
    public void createNetwork(GroupId groupId) {
        if (!groupService.exists(groupId)) {
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

    @Override
    public Server getServer() {
        return server;
    }
}
