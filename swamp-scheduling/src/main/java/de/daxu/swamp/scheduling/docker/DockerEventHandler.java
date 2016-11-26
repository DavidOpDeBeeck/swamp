package de.daxu.swamp.scheduling.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstance;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceScheduledEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceStartedEvent;
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

        containerInstanceWriteService.create( event.getContainerInstanceId(), response.getId(), command.getName() );
    }

    @EventHandler
    public void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();

        createDockerClient( containerInstanceId )
                .startContainerCmd( containerInstanceId.getValue() )
                .exec();

        containerInstanceWriteService.start( containerInstanceId );
    }

    @EventHandler
    public void on( ContainerInstanceStartedEvent event ) {
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();

        // TODO: possible memory leak when container is removed
        createDockerClient( containerInstanceId )
                .logContainerCmd( containerInstanceId.getValue() )
                .withStdErr( true )
                .withStdOut( true )
                .withFollowStream( true )
                .exec( createLogCallback( containerInstanceId ) );

        containerInstanceWriteService.startLogging( containerInstanceId );
    }

    private ResultCallbackTemplate<LogContainerResultCallback, Frame> createLogCallback( final ContainerInstanceId containerInstanceId ) {
        return new ResultCallbackTemplate<LogContainerResultCallback, Frame>() {
            @Override
            public void onNext( Frame frame ) {
                String log = frame.toString().replaceAll( "STDOUT: ", "\n" ).replaceAll( "STDERR: ", "\n" );
                containerInstanceWriteService.receiveLog( containerInstanceId, log );
            }
        };
    }

    private DockerClient createDockerClient( ContainerInstanceId containerInstanceId ) {
        ContainerInstance containerInstance = containerInstanceRepository.load( containerInstanceId );
        return createClient( containerInstance.getServer() );
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = ExposedPort.tcp( internalPort );
        Binding external = Binding.bindPort( externalPort );
        return new PortBinding( external, internal );
    }
}
