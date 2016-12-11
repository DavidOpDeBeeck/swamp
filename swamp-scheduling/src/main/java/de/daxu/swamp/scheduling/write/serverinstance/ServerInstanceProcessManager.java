package de.daxu.swamp.scheduling.write.serverinstance;

import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstance;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceCreatedEvent;
import de.daxu.swamp.scheduling.write.containerinstance.event.ContainerInstanceInitializedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings( "unused" )
public class ServerInstanceProcessManager {

    private final ContainerInstanceWriteService containerInstanceWriteService;
    private final EventSourcingRepository<ServerInstance> serverInstanceRepository;
    private final EventSourcingRepository<ContainerInstance> containerInstanceRepository;

    @Autowired
    public ServerInstanceProcessManager( ContainerInstanceWriteService containerInstanceWriteService,
                                         EventSourcingRepository<ServerInstance> serverInstanceRepository,
                                         EventSourcingRepository<ContainerInstance> containerInstanceRepository ) {
        this.containerInstanceWriteService = containerInstanceWriteService;
        this.serverInstanceRepository = serverInstanceRepository;
        this.containerInstanceRepository = containerInstanceRepository;
    }

    @EventHandler
    public void on( ContainerInstanceInitializedEvent event ) {
        containerInstanceWriteService.create( event.getContainerInstanceId() );
    }

    @EventHandler
    public void on( ContainerInstanceCreatedEvent event ) {
        containerInstanceWriteService.start( event.getContainerInstanceId() );
    }
}
