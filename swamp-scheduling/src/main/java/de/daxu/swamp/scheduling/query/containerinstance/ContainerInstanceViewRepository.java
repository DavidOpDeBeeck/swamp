package de.daxu.swamp.scheduling.query.containerinstance;

import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@QueryRepository
public interface ContainerInstanceViewRepository extends JpaRepository<ContainerInstanceView, String> {

    ContainerInstanceView getByContainerInstanceId( @Param( "container_instance_id" ) ContainerInstanceId containerInstanceId );
    List<ContainerInstanceView> getByStatus( @Param( "status" ) ContainerInstanceStatus status );
}
