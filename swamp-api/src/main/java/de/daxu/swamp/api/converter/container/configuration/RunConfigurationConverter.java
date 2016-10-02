package de.daxu.swamp.api.converter.container.configuration;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.ImageConfigurationDTO;
import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.core.container.configuration.ImageConfiguration;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunConfigurationConverter implements DTOConverter<RunConfiguration, RunConfigurationDTO>, DomainConverter<RunConfigurationDTO, RunConfiguration> {

    @Autowired
    ImageConfigurationConverter imageConfigurationConverter;

    @Override
    public RunConfigurationDTO toDTO( RunConfiguration configuration ) {
        switch ( configuration.getType() ) {
            case IMAGE:
                return imageConfigurationConverter.toDTO( ( ImageConfiguration ) configuration );
            default:
                return null;
        }
    }

    @Override
    public RunConfiguration toDomain( RunConfigurationDTO configuration ) {
        switch ( configuration.type ) {
            case IMAGE:
                return imageConfigurationConverter.toDomain( ( ImageConfigurationDTO ) configuration );
            default:
                return null;
        }
    }
}
