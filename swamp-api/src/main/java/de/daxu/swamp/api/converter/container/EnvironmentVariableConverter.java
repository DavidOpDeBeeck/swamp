package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.EnvironmentVariableDTO;
import de.daxu.swamp.core.container.EnvironmentVariable;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.EnvironmentVariable.EnvironmentVariableBuilder.anEnvironmentVariable;

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
