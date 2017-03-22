package de.daxu.swamp.api.project.converter;

import de.daxu.swamp.api.project.dto.ProjectDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.core.project.Project;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ProjectConverter implements DTOConverter<Project, ProjectDTO> {

    @Override
    public ProjectDTO toDTO(Project project) {
        Set<ContainerTemplate> containerTemplates = project.getContainerTemplates();

        return new ProjectDTO.Builder()
                .withId(project.getId())
                .withName(project.getName())
                .withDescription(project.getDescription())
                .withCreatedAt(project.getCreatedAt())
                .withContainers(containerTemplates == null ? 0 : containerTemplates.size())
                .build();
    }
}
