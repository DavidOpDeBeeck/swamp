package de.daxu.swamp.api.converter.scheduler;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceConverter implements DTOConverter<ContainerInstance, ContainerInstanceDTO> {

    @Override
    public ContainerInstanceDTO toDTO( ContainerInstance containerInstance ) {
        ContainerInstanceDTO dto = new ContainerInstanceDTO();
        dto.projectId = containerInstance.getProject().getId();
        dto.containerId = containerInstance.getContainer().getId();
        dto.status = containerInstance.getStatus();
        dto.startedAt = containerInstance.getStartedAt();
        dto.finishedAt = containerInstance.getFinishedAt();
        dto.logs = containerInstance.getLogs();
        return dto;
    }
}
