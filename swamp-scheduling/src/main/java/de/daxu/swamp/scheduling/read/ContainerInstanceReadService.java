package de.daxu.swamp.scheduling.read;

import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceViewRepository;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerInstanceReadService {

    private final ContainerInstanceViewRepository containerInstanceViewRepository;

    @Autowired
    public ContainerInstanceReadService( ContainerInstanceViewRepository containerInstanceViewRepository ) {
        this.containerInstanceViewRepository = containerInstanceViewRepository;
    }

    public List<ContainerInstanceView> getContainerInstanceViewsByStatus( ContainerInstanceStatus status ) {
        return containerInstanceViewRepository.getByStatus( status );
    }

    public ContainerInstanceView getContainerInstanceViewById( ContainerInstanceId containerInstanceId ) {
        return containerInstanceViewRepository.getByContainerInstanceId( containerInstanceId );
    }
}
