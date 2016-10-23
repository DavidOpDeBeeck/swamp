package de.daxu.swamp.scheduler.repository;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;

import java.util.Set;

public interface SchedulingRepository {

    void addProjectInstance( ProjectInstance projectInstance );

    void updateProjectInstance( ProjectInstance projectInstance );

    ProjectInstance getProjectInstance( String id );

    Set<ProjectInstance> getAllProjectInstances();

    ContainerInstance getContainerInstanceByInternalId( String internalId );
}
