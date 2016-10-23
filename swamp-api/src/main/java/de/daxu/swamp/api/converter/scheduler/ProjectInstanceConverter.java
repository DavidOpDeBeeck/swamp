package de.daxu.swamp.api.converter.scheduler;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.location.ServerConverter;
import de.daxu.swamp.api.dto.scheduler.ProjectInstanceDTO;
import de.daxu.swamp.scheduler.ProjectInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectInstanceConverter implements DTOConverter<ProjectInstance, ProjectInstanceDTO> {

    @Autowired
    ServerConverter serverConverter;

    @Autowired
    ProjectConverter projectConverter;

    @Override
    public ProjectInstanceDTO toDTO( ProjectInstance projectInstance ) {
        ProjectInstanceDTO dto = new ProjectInstanceDTO();
        dto.id = projectInstance.getId();
        dto.project = projectConverter.toDTO( projectInstance.getProject() );
        dto.containers = projectInstance.getContainerInstances().size();
        dto.startedAt = projectInstance.getStartDate();
        return dto;
    }
}
