package de.daxu.swamp.core.scheduler.command.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.SyncDockerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.location.Server;

import java.util.stream.Collectors;

import static de.daxu.swamp.core.scheduler.client.DockerClientFactory.createClient;

public class CreateCommand implements ContainerCommand {

    private Server server;

    public CreateCommand( Server server ) {
        this.server = server;
    }

    @Override
    public CreateContainerCmd execute( Container container ) {
        DockerClient dockerClient = createClient(server);

        CreateContainerCmd createContainerCmd = container.getRunConfiguration().execute( dockerClient );

        createContainerCmd.withPortBindings( container.getPortMappings().stream()
                .map( portMapping -> createPortBinding( portMapping.getInternal(), portMapping.getExternal() ) )
                .collect( Collectors.toList() ) );

        createContainerCmd.withEnv( container.getEnvironmentVariables().stream()
                .map( EnvironmentVariable::toString )
                .collect( Collectors.toList() ) );

        return createContainerCmd;
    }

    public Server getServer() {
        return server;
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = new ExposedPort( internalPort );
        Ports.Binding external = Ports.Binding.bindPort( externalPort );
        return new PortBinding( external, internal );
    }
}
