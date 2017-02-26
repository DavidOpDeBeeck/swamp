package de.daxu.swamp.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.google.common.collect.Sets;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.client.DeployClient;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.Group;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.configurator.DockerRunConfigurator;
import de.daxu.swamp.filestore.FileStore;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.ExposedPort.tcp;
import static com.github.dockerjava.api.model.Ports.Binding.bindPort;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.docker.command.LogContainerCommandCallback.onLogReceived;

public class DockerContainerClient implements ContainerClient, DeployClient {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResultFactory containerResultFactory;
    private final GroupManager groupManager;
    private final Server server;
    private final FileStore fileStore;

    DockerContainerClient(FileStore fileStore,
                          GroupManager groupManager,
                          DockerClientFactory dockerClientFactory,
                          ContainerResultFactory containerResultFactory,
                          Server server) {
        this.fileStore = fileStore;
        this.groupManager = groupManager;
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
        this.server = server;
    }

    @Override
    public ContainerResult create(ContainerConfiguration config) {
        Set<String> warnings = newHashSet();
        GroupId groupId = config.getGroup();

        warnings.addAll(createNetwork(groupId));

        CreateContainerResponse response = configure(config)
                .withHostConfig(createHostConfig(groupId))
                .withPortBindings(extractPortBindings(config))
                .withEnv(extractEnvironmentVariables(config))
                .withAliases(newArrayList(config.getAliases()))
                .exec();

        ContainerId containerId = ContainerId.of(response.getId());

        warnings.addAll(toSet(response.getWarnings()));
        warnings.addAll(connectNetwork(groupId, containerId));

        Group group = groupManager.create(groupId);
        group.addContainer(containerId);

        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    private HostConfig createHostConfig(GroupId groupId) {
        return new HostConfig()
                .withNetworkMode(groupId.getValue());
    }

    private CreateContainerCmd configure(ContainerConfiguration config) {
        return config.getRunConfiguration().configure(configurator());
    }

    private Set<String> createNetwork(GroupId groupId) {
        return catchWarnings(() -> {
            if(groupManager.exists(groupId))
                return;

            dockerClient()
                    .createNetworkCmd()
                    .withDriver("overlay")
                    .withName(groupId.value())
                    .exec();
        });
    }

    private Set<String> connectNetwork(GroupId groupId, ContainerId containerId) {
        return catchWarnings(
                () -> dockerClient()
                        .connectToNetworkCmd()
                        .withContainerId(containerId.value())
                        .withNetworkId(groupId.value()).exec()
        );
    }

    @Override
    public ContainerResult start(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> dockerClient()
                        .startContainerCmd(containerId.value())
                        .exec()
        );
        return containerResultFactory
                .createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult stop(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> dockerClient()
                        .stopContainerCmd(containerId.value())
                        .exec()
        );
        return containerResultFactory
                .createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult remove(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> dockerClient()
                        .removeContainerCmd(containerId.value())
                        .exec()
        );
        return containerResultFactory
                .createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult log(ContainerId containerId, Consumer<String> logCallback) {
        Set<String> warnings = catchWarnings(
                () -> dockerClient()
                        .logContainerCmd(containerId.value())
                        .withStdErr(true)
                        .withStdOut(true)
                        .withFollowStream(true)
                        .exec(onLogReceived(logCallback))
        );
        return containerResultFactory
                .createResponse(containerId, filterNull(warnings));
    }

    @Override
    public boolean exists(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> dockerClient()
                        .inspectContainerCmd(containerId.value())
                        .exec()
        );
        return warnings.isEmpty();
    }

    @Override
    public boolean isRunning(ContainerId containerId) {
        if(!exists(containerId))
            return false;

        String status = dockerClient()
                .inspectContainerCmd(containerId.value())
                .exec()
                .getState()
                .getStatus();

        return status.equals("created") || status.equals("running");
    }

    @Override
    public Server server() {
        return server;
    }

    private DockerClient dockerClient() {
        return dockerClientFactory.createClient(server);
    }

    private DockerRunConfigurator configurator() {
        return new DockerRunConfigurator(dockerClient(), fileStore);
    }

    private Set<String> catchWarnings(Runnable runnable) {
        Set<String> warnings = newHashSet();
        try {
            runnable.run();
        } catch(Exception e) {
            warnings.add(e.getMessage());
        }
        return warnings;
    }

    private Set<String> filterNull(Collection<String> strings) {
        return strings.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }

    private Set<String> toSet(String[] strings) {
        return strings == null ? Sets.newHashSet() : Sets.newHashSet(strings);
    }

    private List<String> extractEnvironmentVariables(ContainerConfiguration config) {
        return config.getEnvironmentVariables().stream()
                .map(EnvironmentVariable::toString)
                .collect(Collectors.toList());
    }

    private List<PortBinding> extractPortBindings(ContainerConfiguration config) {
        return config.getPortMappings().stream()
                .map(this::convertPortMapping)
                .collect(Collectors.toList());
    }

    private PortBinding convertPortMapping(PortMapping portMapping) {
        return createPortBinding(portMapping.getInternal(), portMapping.getExternal());
    }

    private PortBinding createPortBinding(int internalPort, int externalPort) {
        ExposedPort internal = tcp(internalPort);
        Ports.Binding external = bindPort(externalPort);
        return new PortBinding(external, internal);
    }
}
