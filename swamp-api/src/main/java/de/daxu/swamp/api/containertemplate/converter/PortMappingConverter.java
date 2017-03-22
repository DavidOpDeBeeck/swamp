package de.daxu.swamp.api.containertemplate.converter;

import de.daxu.swamp.api.containertemplate.dto.PortMappingDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.containertemplate.PortMapping;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.containertemplate.PortMapping.Builder.aPortMapping;

@Component
public class PortMappingConverter implements DomainConverter<PortMappingDTO, PortMapping>, DTOConverter<PortMapping, PortMappingDTO> {

    @Override
    public PortMappingDTO toDTO(PortMapping portMapping) {
        return new PortMappingDTO.Builder()
                .withInternal(portMapping.getInternal())
                .withExternal(portMapping.getExternal())
                .build();
    }

    @Override
    public PortMapping toDomain(PortMappingDTO dto) {
        return aPortMapping()
                .withInternal(dto.getInternal())
                .withExternal(dto.getExternal())
                .build();
    }
}
