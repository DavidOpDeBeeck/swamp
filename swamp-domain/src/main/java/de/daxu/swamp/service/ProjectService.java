package de.daxu.swamp.service;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;

import java.util.Collection;

public interface ProjectService {

    Project createProject( Project project );

    void deleteProject( Project project );

    Project getProject( String id );

    Collection<Project> getAllProjects();

    Container getContainer( String id );

    Container addContainerToProject( Project project, Container container );

    void removeContainerFromProject( Project project, Container container );
}
