package de.daxu.swamp.api.configuration.converter;

import de.daxu.swamp.api.configuration.dto.DockerfileConfigurationDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.configuration.DockerfileConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.configuration.DockerfileConfiguration.DockerfileConfigurationBuilder.aDockerfileConfigurationBuilder;

@Component
public class DockerfileConfigurationConverter implements DTOConverter<DockerfileConfiguration, DockerfileConfigurationDTO>, DomainConverter<DockerfileConfigurationDTO, DockerfileConfiguration> {

    @Override
    public DockerfileConfigurationDTO toDTO( DockerfileConfiguration configuration ) {
        DockerfileConfigurationDTO dto = new DockerfileConfigurationDTO();
        dto.dockerfile = configuration.getDockerfile();
        dto.type = configuration.getType();
        return dto;
    }

    @Override
    public DockerfileConfiguration toDomain( DockerfileConfigurationDTO dto ) {
        return aDockerfileConfigurationBuilder()
                .withDockerfile( dto.dockerfile )
                .build();
    }
}
