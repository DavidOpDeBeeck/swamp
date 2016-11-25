package de.daxu.swamp.scheduling.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstance;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceScheduledEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.Ports.Binding;
import static de.daxu.swamp.scheduling.docker.client.DockerClientFactory.createClient;

@Component
@SuppressWarnings( "unused" )
public class DockerEventHandler {

    @Autowired
    private ContainerInstanceWriteService containerInstanceWriteService;

    @Autowired
    private EventSourcingRepository<ContainerInstance> containerInstanceRepository;

    @EventHandler
    public void on( ContainerInstanceScheduledEvent event ) {
        DockerClient dockerClient = createClient( event.getServer() );

        CreateContainerCmd command = event.getRunConfiguration()
                .execute( dockerClient );

        command.withPortBindings( event.getPortMappings().stream()
                .map( portMapping -> createPortBinding( portMapping.getInternal(), portMapping.getExternal() ) )
                .collect( Collectors.toList() ) );

        command.withEnv( event.getEnvironmentVariables().stream()
                .map( EnvironmentVariable::toString )
                .collect( Collectors.toList() ) );

        command.withName( event.getContainerInstanceId().getValue() );

        CreateContainerResponse response = command.exec();

        containerInstanceWriteService.createContainerInstance( event.getContainerInstanceId(), response.getId(), command.getName() );
    }

    @EventHandler
    public void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();
        ContainerInstance containerInstance = containerInstanceRepository.load( containerInstanceId );

        createClient( containerInstance.getServer() )
                .startContainerCmd( containerInstanceId.getValue() )
                .exec();

        containerInstanceWriteService.startContainerInstance( containerInstanceId );
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = ExposedPort.tcp( internalPort );
        Binding external = Binding.bindPort( externalPort );
        return new PortBinding( external, internal );
    }
}
