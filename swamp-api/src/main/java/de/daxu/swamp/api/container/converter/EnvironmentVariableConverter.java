package de.daxu.swamp.api.container.converter;

import de.daxu.swamp.api.container.dto.EnvironmentVariableDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.container.EnvironmentVariable;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.EnvironmentVariable.Builder.anEnvironmentVariable;

@Component
public class EnvironmentVariableConverter implements DomainConverter<EnvironmentVariableDTO, EnvironmentVariable>, DTOConverter<EnvironmentVariable, EnvironmentVariableDTO> {

    @Override
    public EnvironmentVariableDTO toDTO( EnvironmentVariable environmentVariable ) {
        EnvironmentVariableDTO dto = new EnvironmentVariableDTO();
        dto.name = environmentVariable.getName();
        dto.value = environmentVariable.getValue();
        return dto;
    }

    @Override
    public EnvironmentVariable toDomain( EnvironmentVariableDTO dto ) {
        return anEnvironmentVariable()
                .withName( dto.name )
                .withValue( dto.value )
                .build();
    }
}
