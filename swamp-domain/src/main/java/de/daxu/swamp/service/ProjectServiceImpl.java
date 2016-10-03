package de.daxu.swamp.service;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.repository.container.ContainerRepository;
import de.daxu.swamp.repository.container.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ContainerRepository containerRepository;

    @Override
    public Project createProject( Project project ) {
        return projectRepository.save( project );
    }

    @Override
    public Container addContainerToProject( Project project, Container container ) {
        containerRepository.save( container );
        project.addContainer( container );
        projectRepository.save( project );
        return container;
    }

    @Override
    public Project getProject( String id ) {
        return projectRepository.findOne( id );
    }

    @Override
    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public void removeContainerFromProject( Project project, Container container ) {
        containerRepository.delete( container );
        project.removeContainer( container );
        projectRepository.save( project );
    }

    @Override
    public Container updateContainer( Container container ) {
        return containerRepository.save( container );
    }

    @Override
    public Project updateProject( Project project ) {
        return projectRepository.save( project );
    }

    @Override
    public Container getContainer( String id ) {
        return containerRepository.findOne( id );
    }

    @Override
    public void deleteProject( Project project ) {
        projectRepository.delete( project );
    }
}
