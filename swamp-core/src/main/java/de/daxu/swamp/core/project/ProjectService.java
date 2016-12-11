package de.daxu.swamp.core.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService( ProjectRepository projectRepository ) {
        this.projectRepository = projectRepository;
    }

    public Project createProject( Project project ) {
        return projectRepository.save( project );
    }

    public Project updateProject( Project project ) {
        return projectRepository.save( project );
    }

    public void deleteProject( Project project ) {
        projectRepository.delete( project );
    }

    public Project getProject( String id ) {
        return projectRepository.findOne( id );
    }

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
