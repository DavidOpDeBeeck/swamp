package de.daxu.swamp.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.PortBinding;
import com.google.common.collect.Sets;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.client.DeployClient;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;
import de.daxu.swamp.deploy.response.ContainerResponseFactory;
import de.daxu.swamp.deploy.response.CreateContainerResponse;
import de.daxu.swamp.docker.client.DockerClientFactory;
import de.daxu.swamp.docker.log.LogCallback;
import de.daxu.swamp.docker.mapper.PortMappingMapper;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;

public class DockerContainerClient implements ContainerClient, DeployClient {

    private final DockerClientFactory dockerClientFactory;
    private final ContainerResponseFactory containerResponseFactory;
    private final PortMappingMapper portMappingMapper;

    private final Server server;

    public DockerContainerClient( DockerClientFactory dockerClientFactory,
                                  ContainerResponseFactory containerResponseFactory,
                                  PortMappingMapper portMappingMapper,
                                  Server server ) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResponseFactory = containerResponseFactory;
        this.portMappingMapper = portMappingMapper;
        this.server = server;
    }

    @Override
    public CreateContainerResponse create( ContainerConfiguration config ) {
        CreateContainerCmd dockerCommand = config.getRunConfiguration()
                .execute( docker() );

        dockerCommand
                .withPortBindings( extractPortBindings( config ) )
                .withEnv( extractEnvironmentVariables( config ) );

        com.github.dockerjava.api.command.CreateContainerResponse response = dockerCommand.exec();

        return containerResponseFactory.createCreateContainerResponse( ContainerId.of( response.getId() ), response.getId(), toSet( response.getWarnings() ) );
    }

    @Override
    public ContainerResponse start( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().startContainerCmd( containerId.getValue() ).exec()
        );

        return containerResponseFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResponse stop( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().stopContainerCmd( containerId.getValue() ).exec()
        );

        return containerResponseFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResponse remove( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().removeContainerCmd( containerId.getValue() ).exec()
        );

        return containerResponseFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResponse log( ContainerId containerId, Consumer<String> logCallback ) {
        Set<String> warnings = catchWarnings(
                () -> docker().logContainerCmd( containerId.getValue() )
                        .withStdErr( true )
                        .withStdOut( true )
                        .withFollowStream( true )
                        .exec( LogCallback.withConsumer( logCallback ) )
        );

        return containerResponseFactory.createResponse( containerId, warnings );
    }

    @Override
    public Server server() {
        return server;
    }

    private DockerClient docker() {
        return dockerClientFactory.createClient( server );
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
        return strings == null ? Sets.newHashSet() : Sets.newHashSet( strings );
    }

    private List<String> extractEnvironmentVariables( ContainerConfiguration config ) {
        return config.getEnvironmentVariables().stream()
                .map( EnvironmentVariable::toString )
                .collect( Collectors.toList() );
    }

    private List<PortBinding> extractPortBindings( ContainerConfiguration config ) {
        return config.getPortMappings().stream()
                .map( portMappingMapper::convert )
                .collect( Collectors.toList() );
    }
}
