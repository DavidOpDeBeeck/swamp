package de.daxu.swamp.core.scheduler;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import de.daxu.swamp.core.container.Container;
import org.springframework.stereotype.Component;

@Component
public class SchedulerImpl implements Scheduler {

    @Override
    public ContainerInstance schedule( Container container ) {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost( "" )
                .withDockerTlsVerify( true )
                .withDockerCertPath( "" )
                .withApiVersion( "1.24" )
                .build();

        DockerClient docker = DockerClientBuilder.getInstance( config ).build();
        CreateContainerResponse response = docker.createContainerCmd( "centos" ).exec();
        return null;
    }
}
