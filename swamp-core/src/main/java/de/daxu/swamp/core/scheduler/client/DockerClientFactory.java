package de.daxu.swamp.core.scheduler.client;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.config.ServerSSLConfig;

public class DockerClientFactory {

    public static DockerClient createClient( Server server ) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost( server.getIp() )
                .withDockerTlsVerify( true )
                .withCustomSslConfig( new ServerSSLConfig( server ) )
                .withApiVersion( "1.24" )
                .build();
        return DockerClientBuilder.getInstance( config ).build();
    }
}
