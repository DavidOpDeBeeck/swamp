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
    public ImageConfigurationDTO toDTO(ImageConfiguration configuration) {
        return new ImageConfigurationDTO.Builder()
                .withName(configuration.getName())
                .build();
    }

    @Override
    public ImageConfiguration toDomain(ImageConfigurationDTO imageConfigurationDTO) {
        return anImageConfiguration()
                .withName(imageConfigurationDTO.getName())
                .build();
    }
}
