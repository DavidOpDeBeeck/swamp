package de.daxu.swamp.api.container.converter;

import de.daxu.swamp.api.container.dto.PortMappingDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.container.PortMapping;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.PortMapping.PortMappingBuilder.aPortMappingBuilder;

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
        return aPortMappingBuilder()
                .withInternal( dto.internal )
                .withExternal( dto.external )
                .build();
    }
}
