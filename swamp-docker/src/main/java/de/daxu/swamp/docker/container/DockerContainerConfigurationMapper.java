package de.daxu.swamp.docker.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.deploy.callback.ProgressCallback;
import de.daxu.swamp.deploy.container.ContainerConfiguration;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.docker.DockerExecutor;
import de.daxu.swamp.docker.DockerRunConfigurator;
import de.daxu.swamp.workspace.manager.WorkspaceManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.ExposedPort.tcp;
import static com.github.dockerjava.api.model.Ports.Binding.bindPort;
import static com.google.common.collect.Lists.newArrayList;

public class DockerContainerConfigurationMapper {

    private final DockerExecutor executor;
    private final WorkspaceManager workspaceManager;

    public DockerContainerConfigurationMapper(DockerExecutor executor,
                                              WorkspaceManager workspaceManager) {
        this.executor = executor;
        this.workspaceManager = workspaceManager;
    }

    public CreateContainerCmd map(ContainerConfiguration config, ProgressCallback<String> progressCallback) {
        return executor.execDockerWithResponse(docker ->
                configure(docker, config, progressCallback)
                        .withHostConfig(hostConfig(config.getGroupId()))
                        .withEnv(environmentVariables(config))
                        .withPortBindings(portBindings(config))
                        .withAliases(newArrayList(config.getAliases()))
        );
    }

    private CreateContainerCmd configure(DockerClient dockerClient,
                                         ContainerConfiguration config,
                                         ProgressCallback<String> progressCallback) {
        DockerRunConfigurator configurator = configurator(dockerClient, progressCallback);
        return config.getRunConfiguration().configure(configurator);
    }

    private DockerRunConfigurator configurator(DockerClient dockerClient, ProgressCallback<String> progressCallback) {
        return new DockerRunConfigurator(dockerClient, workspaceManager, progressCallback);
    }

    private HostConfig hostConfig(GroupId groupId) {
        return new HostConfig()
                .withNetworkMode(groupId.value());
    }

    private List<PortBinding> portBindings(ContainerConfiguration config) {
        return config.getPortMappings().stream()
                .map(this::convertPortMapping)
                .collect(Collectors.toList());
    }

    private List<String> environmentVariables(ContainerConfiguration config) {
        return config.getEnvironmentVariables().stream()
                .map(EnvironmentVariable::toString)
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
