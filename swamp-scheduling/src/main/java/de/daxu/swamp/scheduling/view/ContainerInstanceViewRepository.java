package de.daxu.swamp.scheduling.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerInstanceViewRepository extends JpaRepository<ContainerInstanceView, String> {

}
