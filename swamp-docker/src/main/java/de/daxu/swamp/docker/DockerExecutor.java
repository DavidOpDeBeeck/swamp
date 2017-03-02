package de.daxu.swamp.docker;

import com.github.dockerjava.api.DockerClient;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.docker.client.DockerClientFactory;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public class DockerExecutor {

    private final Server server;
    private final DockerClientFactory dockerClientFactory;
    private final ContainerResultFactory containerResultFactory;

    DockerExecutor(Server server,
                   DockerClientFactory dockerClientFactory,
                   ContainerResultFactory containerResultFactory) {
        this.server = server;
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
    }

    public ContainerResult exec(Function<Set<String>, ContainerId> commandExecution) {
        Set<String> warnings = newHashSet();
        ContainerId containerId = commandExecution.apply(warnings);
        return containerResultFactory.createResponse(containerId, filterEmpty(warnings));
    }

    public ContainerResult exec(Supplier<Set<String>> commandExecution, ContainerId containerId) {
        Set<String> warnings = commandExecution.get();
        return containerResultFactory.createResponse(containerId, filterEmpty(warnings));
    }

    public Set<String> execDocker(Consumer<DockerClient> commandExecution) {
        Set<String> warnings = newHashSet();
        try {
            commandExecution.accept(dockerClient());
        } catch(Exception e) {
            warnings.add(e.getMessage());
        }
        return warnings;
    }

    public <T> T execDockerWithResponse(Function<DockerClient, T> commandExecution) {
        try {
            return commandExecution.apply(dockerClient());
        } catch(Exception e) {
            return null;
        }
    }

    private DockerClient dockerClient() {
        return dockerClientFactory.createClient(server);
    }

    private Set<String> filterEmpty(Collection<String> strings) {
        return strings.stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }
}
