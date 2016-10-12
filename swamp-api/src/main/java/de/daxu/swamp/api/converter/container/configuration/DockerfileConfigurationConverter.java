package de.daxu.swamp.api.converter.container.configuration;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.DockerfileConfigurationDTO;
import de.daxu.swamp.core.container.configuration.DockerfileConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.configuration.DockerfileConfiguration.DockerfileConfigurationBuilder.aDockerfileConfiguration;

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
        return aDockerfileConfiguration()
                .withDockerfile( dto.dockerfile )
                .build();
    }
}