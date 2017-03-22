package de.daxu.swamp.core.project;

import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    public Project getProject(String id) {
        return projectRepository.findOne(id);
    }

    public Collection<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addContainerTemplateToProject(String projectId, ContainerTemplate containerTemplate) {
        Project project = getProject(projectId);
        project.addContainerTemplate(containerTemplate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeContainerTemplateToProject(String projectId, ContainerTemplate containerTemplate) {
        Project project = getProject(projectId);
        project.removeContainerTemplate(containerTemplate);
    }
}
