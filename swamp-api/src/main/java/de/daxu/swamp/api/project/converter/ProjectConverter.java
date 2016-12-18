package de.daxu.swamp.api.project.converter;

import de.daxu.swamp.api.project.dto.ProjectDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.core.project.Project;
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
        dto.containers = project.getContainers().size();
        return dto;
    }
}
