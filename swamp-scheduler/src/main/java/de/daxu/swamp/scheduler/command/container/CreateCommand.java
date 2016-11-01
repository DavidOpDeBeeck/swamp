package de.daxu.swamp.scheduler.command.container;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduler.core.ProjectInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.event.EventHandler;

import java.util.Date;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduler.core.ContainerInstance.ContainerInstanceBuilder.aContainerInstance;
import static de.daxu.swamp.scheduler.client.DockerClientFactory.createClient;

public class CreateCommand extends Command<Container> {

    private ProjectInstance projectInstance;
    private Server server;

    public CreateCommand( EventHandler eventHandler, ProjectInstance projectInstance, Server server ) {
        super( eventHandler );
        this.projectInstance = projectInstance;
        this.server = server;
    }

    @Override
    public void execute( Container container ) {
        DockerClient dockerClient = createClient( server );

        CreateContainerCmd command = container.getRunConfiguration().execute( dockerClient );

        command.withPortBindings( container.getPortMappings().stream()
                .map( portMapping -> createPortBinding( portMapping.getInternal(), portMapping.getExternal() ) )
                .collect( Collectors.toList() ) );

        command.withEnv( container.getEnvironmentVariables().stream()
                .map( EnvironmentVariable::toString )
                .collect( Collectors.toList() ) );

        CreateContainerResponse response = command.exec();

        getEventHandler().onCreate( aContainerInstance()
                .withProjectInstance( projectInstance )
                .withInternalContainerId( response.getId() )
                .withServer( server )
                .withContainer( container )
                .withStartDate( new Date() )
                .build() );
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
