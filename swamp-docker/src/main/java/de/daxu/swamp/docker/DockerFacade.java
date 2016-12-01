package de.daxu.swamp.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.google.common.collect.Sets;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.configuration.CreateContainerConfiguration;
import de.daxu.swamp.deploy.configuration.LogContainerConfiguration;
import de.daxu.swamp.deploy.response.ContainerResponse;
import de.daxu.swamp.deploy.response.ContainerResponseFactory;
import de.daxu.swamp.deploy.response.CreateContainerResponse;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.mapper.EnvironmentVariableMapper;
import de.daxu.swamp.docker.mapper.PortMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

@Service
public class DockerFacade implements DeployFacade {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResponseFactory containerResponseFactory;
    private final PortMappingMapper portMappingMapper;
    private final EnvironmentVariableMapper environmentVariableMapper;

    @Autowired
    public DockerFacade( DockerClientFactory dockerClientFactory,
                         ContainerResponseFactory containerResponseFactory,
                         PortMappingMapper portMappingMapper,
                         EnvironmentVariableMapper environmentVariableMapper ) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResponseFactory = containerResponseFactory;
        this.portMappingMapper = portMappingMapper;
        this.environmentVariableMapper = environmentVariableMapper;
    }

    @Override
    public CreateContainerResponse createContainer( CreateContainerConfiguration config ) {
        DockerClient dockerClient = createDockerClient( config );

        CreateContainerCmd dockerCommand = config.getRunConfiguration()
                .execute( dockerClient );

        dockerCommand.withName( config.getContainerId().getValue() )
                .withPortBindings( extractPortBindings( config ) )
                .withEnv( extractEnvironmentVariables( config ) );

        com.github.dockerjava.api.command.CreateContainerResponse response = dockerCommand.exec();

        return containerResponseFactory.createCreateContainerResponse( config.getContainerId(), response.getId(), toSet( response.getWarnings() ) );
    }

    @Override
    public ContainerResponse startContainer( ContainerConfiguration configuration ) {
        DockerClient dockerClient = createDockerClient( configuration );

        Set<String> warnings = catchWarnings(
                () -> dockerClient.startContainerCmd( configuration.getContainerId().getValue() ).exec()
        );

        return containerResponseFactory.createResponse( configuration.getContainerId(), warnings );
    }

    @Override
    public ContainerResponse stopContainer( ContainerConfiguration configuration ) {
        DockerClient dockerClient = createDockerClient( configuration );

        Set<String> warnings = catchWarnings(
                () -> dockerClient.stopContainerCmd( configuration.getContainerId().getValue() ).exec()
        );

        return containerResponseFactory.createResponse( configuration.getContainerId(), warnings );
    }

    @Override
    public ContainerResponse removeContainer( ContainerConfiguration configuration ) {
        DockerClient dockerClient = createDockerClient( configuration );

        Set<String> warnings = catchWarnings(
                () -> dockerClient.removeContainerCmd( configuration.getContainerId().getValue() ).exec()
        );

        return containerResponseFactory.createResponse( configuration.getContainerId(), warnings );
    }

    @Override
    public ContainerResponse logContainer( LogContainerConfiguration config ) {
        DockerClient dockerClient = createDockerClient( config );
        Consumer<String> logCallback = config.getLogCallback();

        Set<String> warnings = catchWarnings(
                () -> dockerClient.logContainerCmd( config.getContainerId().getValue() )
                        .withStdErr( true )
                        .withStdOut( true )
                        .withFollowStream( true )
                        .exec( createLogCallback( logCallback ) )
        );

        return containerResponseFactory.createResponse( config.getContainerId(), warnings );
    }

    private Set<String> catchWarnings( Runnable runnable ) {
        Set<String> warnings = newHashSet();
        try {
            runnable.run();
        } catch( Exception e ) {
            warnings.add( e.getMessage() );
        }
        return warnings;
    }

    private Set<String> toSet( String[] strings ) {
        return Sets.newHashSet( strings );
    }

    private List<String> extractEnvironmentVariables( CreateContainerConfiguration config ) {
        return config.getEnvironmentVariables().stream()
                .map( environmentVariableMapper::convert )
                .collect( Collectors.toList() );
    }

    private List<PortBinding> extractPortBindings( CreateContainerConfiguration config ) {
        return config.getPortMappings().stream()
                .map( portMappingMapper::convert )
                .collect( Collectors.toList() );
    }

    private DockerClient createDockerClient( ContainerConfiguration configuration ) {
        return dockerClientFactory.createClient( configuration.getServer() );
    }

    private LogCallbackListener createLogCallback( Consumer<String> logCallback ) {
        return new LogCallbackListener( ( frame ) -> logCallback.accept( decodeFrame( frame ) ) );
    }

    private String decodeFrame( Frame frame ) {
        return frame.toString()
                .replaceAll( "STDOUT: ", "\n" )
                .replaceAll( "STDERR: ", "\n" );
    }

    private class LogCallbackListener extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

        private Consumer<Frame> consumer;

        LogCallbackListener( Consumer<Frame> consumer ) {
            this.consumer = consumer;
        }

        @Override
        public void onNext( Frame frame ) {
            this.consumer.accept( frame );
        }
    }
}
