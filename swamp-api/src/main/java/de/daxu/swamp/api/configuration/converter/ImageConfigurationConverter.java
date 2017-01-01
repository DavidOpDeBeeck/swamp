package de.daxu.swamp.api.configuration.converter;

import de.daxu.swamp.api.configuration.dto.ImageConfigurationDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.configuration.ImageConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.configuration.ImageConfiguration.Builder.anImageConfiguration;

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
        return anImageConfiguration()
                .withName( imageConfigurationDTO.name )
                .build();
    }
}
