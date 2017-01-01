package de.daxu.swamp.api.credentials.converter;

import de.daxu.swamp.api.credentials.dto.UsernamePasswordCredentialsDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.credentials.UsernamePasswordCredentials.Builder.anUsernamePasswordCredentials;

@Component
public class UsernamePasswordCredentialsConverter implements DomainConverter<UsernamePasswordCredentialsDTO, UsernamePasswordCredentials>, DTOConverter<UsernamePasswordCredentials, UsernamePasswordCredentialsDTO> {

    @Override
    public UsernamePasswordCredentialsDTO toDTO( UsernamePasswordCredentials credentials ) {
        UsernamePasswordCredentialsDTO dto = new UsernamePasswordCredentialsDTO();
        dto.username = credentials.getUsername();
        dto.password = credentials.getPassword();
        dto.type = credentials.getType();
        return dto;
    }

    @Override
    public UsernamePasswordCredentials toDomain( UsernamePasswordCredentialsDTO dto ) {
        return anUsernamePasswordCredentials()
                .withUsername( dto.username )
                .withPassword( dto.password )
                .build();
    }
}
