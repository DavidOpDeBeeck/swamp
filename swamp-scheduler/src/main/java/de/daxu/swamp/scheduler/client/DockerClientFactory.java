package de.daxu.swamp.scheduler.client;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import de.daxu.swamp.core.location.Server;

public class DockerClientFactory {

    private static final String DOCKER_VERSION = "1.24";

    public static DockerClient createClient( Server server ) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost( server.getIp() )
                .withDockerTlsVerify( true )
                .withCustomSslConfig( new ServerSSLConfig( server ) )
                .withApiVersion( DOCKER_VERSION )
                .build();
        return DockerClientBuilder.getInstance( config ).build();
    }
}
