package de.daxu.swamp.api.configuration.converter;

import de.daxu.swamp.api.configuration.dto.DockerfileConfigurationDTO;
import de.daxu.swamp.api.configuration.dto.GitConfigurationDTO;
import de.daxu.swamp.api.configuration.dto.ImageConfigurationDTO;
import de.daxu.swamp.api.configuration.dto.RunConfigurationDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import de.daxu.swamp.core.configuration.GitConfiguration;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import de.daxu.swamp.core.configuration.RunConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunConfigurationConverter implements DTOConverter<RunConfiguration, RunConfigurationDTO>, DomainConverter<RunConfigurationDTO, RunConfiguration> {

    private final ImageConfigurationConverter imageConfigurationConverter;
    private final GitConfigurationConverter gitConfigurationConverter;
    private final DockerfileConfigurationConverter dockerfileConfigurationConverter;

    @Autowired
    public RunConfigurationConverter(ImageConfigurationConverter imageConfigurationConverter, GitConfigurationConverter gitConfigurationConverter, DockerfileConfigurationConverter dockerfileConfigurationConverter) {
        this.imageConfigurationConverter = imageConfigurationConverter;
        this.gitConfigurationConverter = gitConfigurationConverter;
        this.dockerfileConfigurationConverter = dockerfileConfigurationConverter;
    }

    @Override
    public RunConfigurationDTO toDTO(RunConfiguration configuration) {
        switch (configuration.getType()) {
            case IMAGE:
                return imageConfigurationConverter.toDTO((ImageConfiguration) configuration);
            case GIT:
                return gitConfigurationConverter.toDTO((GitConfiguration) configuration);
            case DOCKERFILE:
                return dockerfileConfigurationConverter.toDTO((DockerfileConfiguration) configuration);
            default:
                return null;
        }
    }

    @Override
    public RunConfiguration toDomain(RunConfigurationDTO configuration) {
        switch (configuration.getType()) {
            case IMAGE:
                return imageConfigurationConverter.toDomain((ImageConfigurationDTO) configuration);
            case GIT:
                return gitConfigurationConverter.toDomain((GitConfigurationDTO) configuration);
            case DOCKERFILE:
                return dockerfileConfigurationConverter.toDomain((DockerfileConfigurationDTO) configuration);
            default:
                return null;
        }
    }
}
