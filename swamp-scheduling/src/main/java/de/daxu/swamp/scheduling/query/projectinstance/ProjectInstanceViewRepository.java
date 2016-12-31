package de.daxu.swamp.scheduling.query.projectinstance;

import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectInstanceViewRepository extends JpaRepository<ProjectInstanceView, String> {

    ProjectInstanceView getByProjectInstanceId( @Param( "project_instance_id" ) ProjectInstanceId projectInstanceId );
}
