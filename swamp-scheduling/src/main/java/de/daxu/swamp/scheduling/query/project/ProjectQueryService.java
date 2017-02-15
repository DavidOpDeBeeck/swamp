package de.daxu.swamp.scheduling.query.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectQueryService {

    private final ProjectViewRepository projectViewRepository;

    @Autowired
    public ProjectQueryService(ProjectViewRepository projectViewRepository) {
        this.projectViewRepository = projectViewRepository;
    }

    public int getBuildSequence(String name) {
        ProjectView view = projectViewRepository.getByName(name);
        if (view == null) return 0;
        return view.getBuildSequence();
    }

    public List<ProjectView> getAllProjectViews() {
        return projectViewRepository.findAll();
    }
}
