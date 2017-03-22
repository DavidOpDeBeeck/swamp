package de.daxu.swamp.api.version.converter;

import de.daxu.swamp.api.version.dto.VersionDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import org.springframework.stereotype.Component;

@Component
public class VersionConverter implements DTOConverter<String, VersionDTO> {

    @Override
    public VersionDTO toDTO( String version ) {
        return new VersionDTO(version);
    }
}
