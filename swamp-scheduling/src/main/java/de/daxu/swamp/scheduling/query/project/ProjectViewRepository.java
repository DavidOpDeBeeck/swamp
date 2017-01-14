package de.daxu.swamp.scheduling.query.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectViewRepository extends JpaRepository<ProjectView, String> {

    ProjectView getByName( @Param( "name" ) String name );
}
