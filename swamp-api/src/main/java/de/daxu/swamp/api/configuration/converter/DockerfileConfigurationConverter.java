package de.daxu.swamp.api.configuration.converter;

import de.daxu.swamp.api.configuration.dto.DockerfileConfigurationDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.configuration.DockerfileConfiguration.Builder.aDockerfileConfiguration;

@Component
public class DockerfileConfigurationConverter implements DTOConverter<DockerfileConfiguration, DockerfileConfigurationDTO>, DomainConverter<DockerfileConfigurationDTO, DockerfileConfiguration> {

    @Override
    public DockerfileConfigurationDTO toDTO(DockerfileConfiguration configuration) {
        return new DockerfileConfigurationDTO.Builder()
                .withDockerfile(configuration.getDockerfile())
                .build();
    }

    @Override
    public DockerfileConfiguration toDomain(DockerfileConfigurationDTO dto) {
        return aDockerfileConfiguration()
                .withDockerfile(dto.getDockerfile())
                .build();
    }
}
