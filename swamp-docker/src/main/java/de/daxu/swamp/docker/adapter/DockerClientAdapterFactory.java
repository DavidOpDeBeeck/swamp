package de.daxu.swamp.docker.adapter;

import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.group.GroupService;
import de.daxu.swamp.docker.adapter.command.DockerCreateContainerCommandAdapterFactory;
import de.daxu.swamp.docker.behaviour.DockerBehaviour;
import de.daxu.swamp.docker.behaviour.DockerBehaviourFactory;
import org.springframework.stereotype.Component;

import static com.github.dockerjava.core.DefaultDockerClientConfig.createDefaultConfigBuilder;

@Component
public class DockerClientAdapterFactory implements DockerBehaviourFactory {

    private static final String DOCKER_VERSION = "1.22";

    private final GroupService groupService;
    private final DockerCreateContainerCommandAdapterFactory createCommandFactory;

    public DockerClientAdapterFactory(GroupService groupService,
                                      DockerCreateContainerCommandAdapterFactory createCommandFactory) {
        this.groupService = groupService;
        this.createCommandFactory = createCommandFactory;
    }

    @Override
    public DockerBehaviour createBehaviour(Server server) {
        DockerClientConfig config = createDefaultConfigBuilder()
                .withDockerHost(server.getIp())
                .withDockerTlsVerify(true)
                .withCustomSslConfig(new DockerClientSSLConfig(server))
                .withApiVersion(DOCKER_VERSION)
                .build();

        com.github.dockerjava.api.DockerClient client
                = DockerClientBuilder.getInstance(config).build();

        return new DockerClientAdapter(server, client, groupService, createCommandFactory);
    }
}
