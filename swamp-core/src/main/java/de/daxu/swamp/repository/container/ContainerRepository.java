package de.daxu.swamp.repository.container;

import de.daxu.swamp.core.container.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, String> {

}
