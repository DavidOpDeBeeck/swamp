package de.daxu.swamp.docker.adapter.command;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.daxu.swamp.core.containertemplate.EnvironmentVariable;
import de.daxu.swamp.core.containertemplate.PortMapping;
import de.daxu.swamp.deploy.group.GroupId;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.ExposedPort.tcp;
import static com.github.dockerjava.api.model.Ports.Binding.bindPort;
import static com.google.common.collect.Lists.newArrayList;

public class DockerCreateContainerCommandAdapter {

    private String tag;
    private HostConfig hostConfig;
    private List<String> aliases;
    private List<PortBinding> portBindings;
    private List<String> environmentVariables;

    public DockerCreateContainerCommandAdapter withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public DockerCreateContainerCommandAdapter withGroup(GroupId groupId) {
        this.hostConfig = new HostConfig().withNetworkMode(groupId.value());
        return this;
    }

    public DockerCreateContainerCommandAdapter withAliases(Set<String> aliases) {
        this.aliases = newArrayList(aliases);
        return this;
    }

    public DockerCreateContainerCommandAdapter withPortMappings(Set<PortMapping> portMappings) {
        this.portBindings = portMappings.stream()
                .map(this::mapPortMapping)
                .collect(Collectors.toList());
        return this;
    }

    public DockerCreateContainerCommandAdapter withEnvironmentVariables(Set<EnvironmentVariable> environmentVariables) {
        this.environmentVariables = environmentVariables.stream()
                .map(EnvironmentVariable::asString)
                .collect(Collectors.toList());
        return this;
    }

    public CreateContainerCmd build(com.github.dockerjava.api.DockerClient client) {
        return client.createContainerCmd(tag)
                .withHostConfig(hostConfig)
                .withEnv(environmentVariables)
                .withPortBindings(portBindings)
                .withAliases(aliases);
    }

    private PortBinding mapPortMapping(PortMapping portMapping) {
        return createPortBinding(portMapping.getInternal(), portMapping.getExternal());
    }

    private PortBinding createPortBinding(int internalPort, int externalPort) {
        ExposedPort internal = tcp(internalPort);
        Ports.Binding external = bindPort(externalPort);
        return new PortBinding(external, internal);
    }
}
