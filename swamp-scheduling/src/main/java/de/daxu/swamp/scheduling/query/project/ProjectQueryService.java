package de.daxu.swamp.scheduling.query.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectQueryService {

    private final ProjectViewRepository projectViewRepository;

    @Autowired
    public ProjectQueryService( ProjectViewRepository projectViewRepository ) {
        this.projectViewRepository = projectViewRepository;
    }

    public List<ProjectView> getAllProjectViews() {
        return projectViewRepository.findAll( );
    }
}
