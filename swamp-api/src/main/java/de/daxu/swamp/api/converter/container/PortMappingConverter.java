package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.PortMappingDTO;
import de.daxu.swamp.core.container.PortMapping;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.PortMapping.PortMappingBuilder.aPortMapping;

@Component
public class PortMappingConverter implements DomainConverter<PortMappingDTO, PortMapping>, DTOConverter<PortMapping, PortMappingDTO> {

    @Override
    public PortMappingDTO toDTO( PortMapping portMapping ) {
        PortMappingDTO dto = new PortMappingDTO();
        dto.internal = portMapping.getInternal();
        dto.external = portMapping.getExternal();
        return dto;
    }

    @Override
    public PortMapping toDomain( PortMappingDTO dto ) {
        return aPortMapping()
                .withInternal( dto.internal )
                .withExternal( dto.external )
                .build();
    }
}
