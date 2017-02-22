package de.daxu.swamp.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
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
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.configurator.DockerRunConfigurator;
import de.daxu.swamp.docker.log.LogCallback;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.ExposedPort.tcp;
import static com.github.dockerjava.api.model.Ports.Binding.bindPort;
import static com.google.common.collect.Sets.newHashSet;

public class DockerContainerClient implements ContainerClient, DeployClient {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResultFactory containerResultFactory;
    private final GroupManager groupManager;

    private final Server server;

    DockerContainerClient(DockerClientFactory dockerClientFactory,
                          ContainerResultFactory containerResultFactory,
                          GroupManager groupManager,
                          Server server) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
        this.groupManager = groupManager;
        this.server = server;
    }

    @Override
    public ContainerResult create(ContainerConfiguration config) {
        Set<String> warnings = newHashSet();

        GroupId groupId = config.getGroup();
        warnings.addAll(createNetworkIfGroupIsNew(groupId));

        CreateContainerCmd dockerCommand = config.getRunConfiguration()
                .configure(configurator());

        dockerCommand
                .withPortBindings(extractPortBindings(config))
                .withHostConfig(new HostConfig()
                        .withNetworkMode(groupId.getValue()))
                .withAliases(new ArrayList<>(config.getAliases()))
                .withEnv(extractEnvironmentVariables(config));

        CreateContainerResponse response = dockerCommand.exec();

        ContainerId containerId = ContainerId.of(response.getId());

        warnings.addAll(connectToGroupNetwork(groupId, containerId));
        warnings.addAll(toSet(response.getWarnings()));

        groupManager.addContainerToGroup(groupId, containerId);

        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    private Set<String> createNetworkIfGroupIsNew(GroupId groupId) {
        return catchWarnings(
                () -> {
                    if(!groupManager.exists(groupId)) {
                        docker().createNetworkCmd()
                                .withDriver("overlay")
                                .withName(groupId.getValue()).exec();
                    }
                }
        );
    }

    private Set<String> connectToGroupNetwork(GroupId groupId, ContainerId containerId) {
        return catchWarnings(
                () -> docker().connectToNetworkCmd()
                        .withContainerId(containerId.getValue())
                        .withNetworkId(groupId.getValue()).exec()
        );
    }

    @Override
    public ContainerResult start(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> docker().startContainerCmd(containerId.getValue()).exec()
        );
        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult stop(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> docker().stopContainerCmd(containerId.getValue()).exec()
        );
        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult remove(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> docker().removeContainerCmd(containerId.getValue()).exec()
        );
        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    @Override
    public ContainerResult log(ContainerId containerId, Consumer<String> logCallback) {
        Set<String> warnings = catchWarnings(
                () -> docker().logContainerCmd(containerId.getValue())
                        .withStdErr(true)
                        .withStdOut(true)
                        .withFollowStream(true)
                        .exec(LogCallback.withConsumer(logCallback))
        );
        return containerResultFactory.createResponse(containerId, filterNull(warnings));
    }

    @Override
    public boolean exists(ContainerId containerId) {
        Set<String> warnings = catchWarnings(
                () -> docker().inspectContainerCmd(containerId.getValue()).exec()
        );
        return warnings.isEmpty(); // TODO: find a better way
    }

    @Override
    public boolean isRunning(ContainerId containerId) {
        if(!exists(containerId)) return false;
        InspectContainerResponse response = docker().inspectContainerCmd(containerId.getValue()).exec();
        return response.getState().getStatus().equals("created")
                || response.getState().getStatus().equals("running");
    }

    @Override
    public Server server() {
        return server;
    }

    private DockerClient docker() {
        return dockerClientFactory.createClient(server);
    }

    private DockerRunConfigurator configurator() {
        return new DockerRunConfigurator(docker());
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
