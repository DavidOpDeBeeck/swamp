package de.daxu.swamp.scheduler.scheduling;

import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.core.ProjectInstance;

import java.util.Set;

public interface SchedulingService {

    void addProjectInstance( ProjectInstance projectInstance );

    void updateProjectInstance( ProjectInstance projectInstance );

    ProjectInstance getProjectInstance( String id );

    Set<ProjectInstance> getAllProjectInstances();

    ContainerInstance getContainerInstanceByInternalId( String internalId );

}
