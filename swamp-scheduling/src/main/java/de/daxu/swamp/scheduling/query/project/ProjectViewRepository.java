package de.daxu.swamp.scheduling.query.project;

import de.daxu.swamp.common.axon.QueryRepository;
import de.daxu.swamp.scheduling.command.project.ProjectId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

@QueryRepository
public interface ProjectViewRepository extends JpaRepository<ProjectView, String> {

    ProjectView getByName( @Param( "name" ) String name );

    ProjectView getByProjectId( @Param( "project_id" ) ProjectId projectId );
}
