package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.core.container.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements DTOConverter<Project, ProjectDTO> {

    @Override
    public ProjectDTO toDTO( Project project ) {
        ProjectDTO dto = new ProjectDTO();
        dto.id = project.getId();
        dto.name = project.getName();
        dto.description = project.getDescription();
        dto.created = project.getCreated();
        return dto;
    }
}
