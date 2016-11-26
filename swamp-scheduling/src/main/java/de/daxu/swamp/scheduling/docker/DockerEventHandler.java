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
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.docker.client.DockerClientFactory;
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

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.github.dockerjava.api.model.Ports.Binding;

@Component
@SuppressWarnings( "unused" )
public class DockerEventHandler {

    private final ContainerInstanceWriteService containerInstanceWriteService;
    private final EventSourcingRepository<ContainerInstance> containerInstanceRepository;
    private final DockerClientFactory dockerClientFactory;

    @Autowired
    public DockerEventHandler( ContainerInstanceWriteService containerInstanceWriteService,
                               EventSourcingRepository<ContainerInstance> containerInstanceRepository,
                               DockerClientFactory dockerClientFactory ) {
        this.containerInstanceWriteService = containerInstanceWriteService;
        this.containerInstanceRepository = containerInstanceRepository;
        this.dockerClientFactory = dockerClientFactory;
    }

    @EventHandler
    public void on( ContainerInstanceScheduledEvent event ) {
        DockerClient dockerClient = createDockerClient( event.getServer() );
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();
        List<PortBinding> portBindings = event.getPortMappings()
                .stream()
                .map( portMapping -> createPortBinding( portMapping.getInternal(), portMapping.getExternal() ) )
                .collect( Collectors.toList() );
        List<String> environmentVariables = event.getEnvironmentVariables()
                .stream()
                .map( EnvironmentVariable::toString )
                .collect( Collectors.toList() );

        CreateContainerCmd command = event.getRunConfiguration()
                .execute( dockerClient );

        command.withPortBindings( portBindings );
        command.withEnv( environmentVariables );
        command.withName( containerInstanceId.getValue() );

        CreateContainerResponse response = command.exec();

        containerInstanceWriteService.create( containerInstanceId, response.getId(), command.getName() );
    }

    @EventHandler
    public void on( ContainerInstanceCreatedEvent event ) {
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();
        ContainerInstance containerInstance = containerInstanceRepository.load( containerInstanceId );

        createDockerClient( containerInstance.getServer() )
                .startContainerCmd( containerInstanceId.getValue() )
                .exec();

        containerInstanceWriteService.start( containerInstanceId );
    }

    @EventHandler
    public void on( ContainerInstanceStartedEvent event ) {
        ContainerInstanceId containerInstanceId = event.getContainerInstanceId();
        ContainerInstance containerInstance = containerInstanceRepository.load( containerInstanceId );

        // TODO: possible memory leak when container is removed
        createDockerClient( containerInstance.getServer() )
                .logContainerCmd( containerInstanceId.getValue() )
                .withStdErr( true )
                .withStdOut( true )
                .withFollowStream( true )
                .exec( createLogCallback( containerInstanceId ) );

        containerInstanceWriteService.startLogging( containerInstanceId );
    }

    private DockerClient createDockerClient( Server server ) {
        return dockerClientFactory.createClient( server );
    }

    private PortBinding createPortBinding( int internalPort, int externalPort ) {
        ExposedPort internal = ExposedPort.tcp( internalPort );
        Binding external = Binding.bindPort( externalPort );
        return new PortBinding( external, internal );
    }

    private LogCallback createLogCallback( final ContainerInstanceId containerInstanceId ) {
        return new LogCallback( ( frame ) -> {
            String log = frame.toString().replaceAll( "STDOUT: ", "\n" ).replaceAll( "STDERR: ", "\n" );
            containerInstanceWriteService.receiveLog( containerInstanceId, log );
        } );
    }

    private class LogCallback extends ResultCallbackTemplate<LogContainerResultCallback, Frame> {

        private Consumer<Frame> consumer;

        public LogCallback( Consumer<Frame> consumer ) {
            this.consumer = consumer;
        }

        @Override
        public void onNext( Frame frame ) {
            this.consumer.accept( frame );
        }
    }
}
