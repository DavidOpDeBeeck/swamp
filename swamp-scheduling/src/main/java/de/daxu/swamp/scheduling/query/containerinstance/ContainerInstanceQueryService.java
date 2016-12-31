package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerInstanceQueryService {

    private final ContainerInstanceViewRepository containerInstanceViewRepository;

    @Autowired
    public ContainerInstanceQueryService( ContainerInstanceViewRepository containerInstanceViewRepository ) {
        this.containerInstanceViewRepository = containerInstanceViewRepository;
    }

    public List<ContainerInstanceView> getContainerInstanceViewsByStatus( ContainerInstanceStatus status ) {
        return containerInstanceViewRepository.getByStatus( status );
    }

    public ContainerInstanceView getContainerInstanceViewById( ContainerInstanceId containerInstanceId ) {
        return containerInstanceViewRepository.getByContainerInstanceId( containerInstanceId );
    }
}
