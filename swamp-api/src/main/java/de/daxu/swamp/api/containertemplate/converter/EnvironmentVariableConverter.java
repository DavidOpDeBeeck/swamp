package de.daxu.swamp.api.containertemplate.converter;

import de.daxu.swamp.api.containertemplate.dto.EnvironmentVariableDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.containertemplate.EnvironmentVariable;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.containertemplate.EnvironmentVariable.Builder.anEnvironmentVariable;

@Component
public class EnvironmentVariableConverter implements DomainConverter<EnvironmentVariableDTO, EnvironmentVariable>, DTOConverter<EnvironmentVariable, EnvironmentVariableDTO> {

    @Override
    public EnvironmentVariableDTO toDTO(EnvironmentVariable environmentVariable) {
        return new EnvironmentVariableDTO.Builder()
                .withName(environmentVariable.getName())
                .withValue(environmentVariable.getValue())
                .build();
    }

    @Override
    public EnvironmentVariable toDomain(EnvironmentVariableDTO dto) {
        return anEnvironmentVariable()
                .withName(dto.getName())
                .withValue(dto.getValue())
                .build();
    }
}
