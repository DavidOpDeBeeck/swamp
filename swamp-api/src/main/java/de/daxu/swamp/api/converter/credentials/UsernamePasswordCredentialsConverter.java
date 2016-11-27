package de.daxu.swamp.api.converter.credentials;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.credentials.UsernamePasswordCredentialsDTO;
import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.credentials.UsernamePasswordCredentials.Builder.aUsernamePasswordCredentials;

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
        return aUsernamePasswordCredentials()
                .withUsername( dto.username )
                .withPassword( dto.password )
                .build();
    }
}
