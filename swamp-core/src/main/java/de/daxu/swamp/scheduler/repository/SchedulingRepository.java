package de.daxu.swamp.scheduler.repository;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.ContainerInstance;

import java.util.Set;

public interface SchedulingRepository {

    void addContainerToProject( Project project, Container container );

    Set<Project> getAllProjects();

    void addInstance( ContainerInstance instance );

    void updateInstance( ContainerInstance instance );

    void removeInstance( ContainerInstance instance );

    ContainerInstance getInstance( String internalId );

    ContainerInstance getInstance( Container container );

    Set<ContainerInstance> getAllInstances();

}
