package de.daxu.swamp.docker.adapter;

import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapterFactory;
import de.daxu.swamp.docker.client.DockerClient;
import de.daxu.swamp.docker.client.DockerClientFactory;
import org.springframework.stereotype.Component;

import static com.github.dockerjava.core.DefaultDockerClientConfig.createDefaultConfigBuilder;

@Component
public class DockerClientAdapterFactory implements DockerClientFactory {

    private static final String DOCKER_VERSION = "1.22";

    private final GroupManager groupManager;
    private final DockerCreateContainerCommandAdapterFactory createCommandFactory;

    public DockerClientAdapterFactory(GroupManager groupManager,
                                      DockerCreateContainerCommandAdapterFactory createCommandFactory) {
        this.groupManager = groupManager;
        this.createCommandFactory = createCommandFactory;
    }

    @Override
    public DockerClient createClient(Server server) {
        DockerClientConfig config = createDefaultConfigBuilder()
                .withDockerHost(server.getIp())
                .withDockerTlsVerify(true)
                .withCustomSslConfig(new DockerServerSSLConfig(server))
                .withApiVersion(DOCKER_VERSION)
                .build();

        com.github.dockerjava.api.DockerClient client
                = DockerClientBuilder.getInstance(config).build();

        return new DockerClientAdapter(server, client, groupManager, createCommandFactory);
    }
}
