package de.daxu.swamp.scheduling.read.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerInstanceViewRepository extends JpaRepository<ContainerInstanceView, String> {

    ContainerInstanceView getByContainerInstanceId( @Param( "container_instance_id" ) ContainerInstanceId containerInstanceId );
    List<ContainerInstanceView> getByStatus( @Param( "status" ) ContainerInstanceStatus status );
}
