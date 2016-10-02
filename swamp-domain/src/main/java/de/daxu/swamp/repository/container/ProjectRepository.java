package de.daxu.swamp.repository.container;

import de.daxu.swamp.core.container.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

}
