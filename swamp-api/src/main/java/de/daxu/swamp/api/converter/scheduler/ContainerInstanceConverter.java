package de.daxu.swamp.api.converter.scheduler;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.location.ServerConverter;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.scheduler.ContainerInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerInstanceConverter implements DTOConverter<ContainerInstance, ContainerInstanceDTO> {

    @Autowired
    ServerConverter serverConverter;

    @Autowired
    ContainerConverter containerConverter;

    @Override
    public ContainerInstanceDTO toDTO( ContainerInstance containerInstance ) {
        ContainerInstanceDTO dto = new ContainerInstanceDTO();
        dto.id = containerInstance.getId();
        dto.container = containerConverter.toDTO( containerInstance.getContainer() );
        dto.server = serverConverter.toDTO( containerInstance.getServer() );
        dto.status = containerInstance.getStatus();
        dto.status = containerInstance.getStatus();
        dto.startedAt = containerInstance.getStartedAt();
        dto.finishedAt = containerInstance.getFinishedAt();
        dto.logs = containerInstance.getLogs();
        return dto;
    }
}
