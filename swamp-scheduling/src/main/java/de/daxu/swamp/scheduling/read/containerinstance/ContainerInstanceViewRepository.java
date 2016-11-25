package de.daxu.swamp.scheduling.read.containerinstance;

import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerInstanceViewRepository extends JpaRepository<ContainerInstanceView, String> {

    ContainerInstanceView getByContainerInstanceId( @Param( "container_instance_id" ) ContainerInstanceId containerInstanceId );
}
