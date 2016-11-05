package de.daxu.swamp.scheduling.read.containerinstance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerInstanceViewRepository extends JpaRepository<ContainerInstanceView, String> {

}
