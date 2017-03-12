package de.daxu.swamp.scheduling.query.log;

import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@QueryRepository
public interface LogViewRepository extends JpaRepository<LogView, String> {

    LogView getByContainerInstanceId(@Param("container_instance_id") ContainerInstanceId containerInstanceId);
}
