package de.daxu.swamp.docker;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.PortBinding;
import com.google.common.collect.Sets;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.client.DeployClient;
import de.daxu.swamp.deploy.configuration.ContainerConfiguration;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.group.GroupId;
import de.daxu.swamp.deploy.group.GroupManager;
import de.daxu.swamp.deploy.result.ContainerResult;
import de.daxu.swamp.deploy.result.ContainerResultFactory;
import de.daxu.swamp.deploy.result.CreateContainerResult;
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
    private final ContainerResultFactory containerResultFactory;
    private final PortMappingMapper portMappingMapper;
    private final GroupManager groupManager;

    private final Server server;

    DockerContainerClient( DockerClientFactory dockerClientFactory,
                           ContainerResultFactory containerResultFactory,
                           PortMappingMapper portMappingMapper,
                           GroupManager groupManager,
                           Server server ) {
        this.dockerClientFactory = dockerClientFactory;
        this.containerResultFactory = containerResultFactory;
        this.portMappingMapper = portMappingMapper;
        this.groupManager = groupManager;
        this.server = server;
    }

    @Override
    public CreateContainerResult create( ContainerConfiguration config ) {
        CreateContainerCmd dockerCommand = config.getRunConfiguration()
                .execute( docker() );

        dockerCommand
                .withPortBindings( extractPortBindings( config ) )
                .withEnv( extractEnvironmentVariables( config ) );

        CreateContainerResponse response = dockerCommand.exec();

        GroupId groupId = config.getGroup();
        ContainerId containerId = ContainerId.of( response.getId() );

        Set<String> warnings = newHashSet();
        warnings.addAll( createNetworkIfGroupIsNew( groupId ) );
        warnings.addAll( connectToGroupNetwork( groupId, containerId ) );
        warnings.addAll( toSet( response.getWarnings() ) );

        groupManager.addContainer( groupId, containerId );

        return containerResultFactory.createCreateContainerResponse( containerId, response.getId(), warnings );
    }

    private Set<String> createNetworkIfGroupIsNew( GroupId groupId ) {
        return catchWarnings(
                () -> {
                    if( !groupManager.exists( groupId ) ) {
                        groupManager.addGroup( groupId );
                        CreateNetworkResponse networkResponse = docker().createNetworkCmd()
                                .withDriver( "overlay" )
                                .withIpam( new Ipam() )
                                .withName( groupId.getValue() ).exec();
                        groupManager.addGroupMetaData( groupId, "network.id", networkResponse.getId() );
                    }
                }
        );
    }

    class Ipam extends Network.Ipam {

        @JsonProperty( "Driver" )
        private String driver = "default";

        public String getDriver() {
            return driver;
        }
    }

    private Set<String> connectToGroupNetwork( GroupId groupId, ContainerId containerId ) {
        String networkId = groupManager.getGroupMetaData( groupId, "network.id" );
        return catchWarnings(
                () -> docker().connectToNetworkCmd()
                        .withContainerId( containerId.getValue() )
                        .withNetworkId( networkId ).exec()
        );
    }

    @Override
    public ContainerResult start( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().startContainerCmd( containerId.getValue() ).exec()
        );
        return containerResultFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResult stop( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().stopContainerCmd( containerId.getValue() ).exec()
        );
        return containerResultFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResult remove( ContainerId containerId ) {
        Set<String> warnings = catchWarnings(
                () -> docker().removeContainerCmd( containerId.getValue() ).exec()
        );
        return containerResultFactory.createResponse( containerId, warnings );
    }

    @Override
    public ContainerResult log( ContainerId containerId, Consumer<String> logCallback ) {
        Set<String> warnings = catchWarnings(
                () -> docker().logContainerCmd( containerId.getValue() )
                        .withStdErr( true )
                        .withStdOut( true )
                        .withFollowStream( true )
                        .exec( LogCallback.withConsumer( logCallback ) )
        );
        return containerResultFactory.createResponse( containerId, warnings );
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
