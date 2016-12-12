package de.daxu.swamp.api.converter.container.configuration;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.ImageConfigurationDTO;
import de.daxu.swamp.core.runconfiguration.ImageConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.runconfiguration.ImageConfiguration.ImageConfigurationBuilder.anImageConfigurationBuilder;

@Component
public class ImageConfigurationConverter implements DTOConverter<ImageConfiguration, ImageConfigurationDTO>, DomainConverter<ImageConfigurationDTO, ImageConfiguration> {

    @Override
    public ImageConfigurationDTO toDTO( ImageConfiguration configuration ) {
        ImageConfigurationDTO dto = new ImageConfigurationDTO();
        dto.name = configuration.getName();
        dto.type = configuration.getType();
        return dto;
    }

    @Override
    public ImageConfiguration toDomain( ImageConfigurationDTO imageConfigurationDTO ) {
        return anImageConfigurationBuilder()
                .withName( imageConfigurationDTO.name )
                .build();
    }
}
