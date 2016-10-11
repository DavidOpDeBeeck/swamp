package de.daxu.swamp.api.converter.container.configuration;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.DockerfileConfigurationDTO;
import de.daxu.swamp.api.dto.container.configuration.GitConfigurationDTO;
import de.daxu.swamp.api.dto.container.configuration.ImageConfigurationDTO;
import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.core.container.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.container.configuration.GitConfiguration;
import de.daxu.swamp.core.container.configuration.ImageConfiguration;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunConfigurationConverter implements DTOConverter<RunConfiguration, RunConfigurationDTO>, DomainConverter<RunConfigurationDTO, RunConfiguration> {

    @Autowired
    ImageConfigurationConverter imageConfigurationConverter;

    @Autowired
    GitConfigurationConverter gitConfigurationConverter;

    @Autowired
    DockerfileConfigurationConverter dockerfileConfigurationConverter;

    @Override
    public RunConfigurationDTO toDTO( RunConfiguration configuration ) {
        switch ( configuration.getType() ) {
            case IMAGE:
                return imageConfigurationConverter.toDTO( ( ImageConfiguration ) configuration );
            case GIT:
                return gitConfigurationConverter.toDTO( ( GitConfiguration ) configuration );
            case DOCKERFILE:
                return dockerfileConfigurationConverter.toDTO( ( DockerfileConfiguration ) configuration );
            default:
                return null;
        }
    }

    @Override
    public RunConfiguration toDomain( RunConfigurationDTO configuration ) {
        switch ( configuration.type ) {
            case IMAGE:
                return imageConfigurationConverter.toDomain( ( ImageConfigurationDTO ) configuration );
            case GIT:
                return gitConfigurationConverter.toDomain( ( GitConfigurationDTO ) configuration );
            case DOCKERFILE:
                return dockerfileConfigurationConverter.toDomain( ( DockerfileConfigurationDTO ) configuration );
            default:
                return null;
        }
    }
}
