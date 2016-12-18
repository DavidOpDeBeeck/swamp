package de.daxu.swamp.api.credentials.converter;

import de.daxu.swamp.api.credentials.dto.CredentialsDTO;
import de.daxu.swamp.api.credentials.dto.UsernamePasswordCredentialsDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.credentials.Credentials;
import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CredentialsConverter implements DomainConverter<CredentialsDTO, Credentials>, DTOConverter<Credentials,CredentialsDTO> {

    private final UsernamePasswordCredentialsConverter usernamePasswordCredentialsConverter;

    @Autowired
    public CredentialsConverter( UsernamePasswordCredentialsConverter usernamePasswordCredentialsConverter ) {
        this.usernamePasswordCredentialsConverter = usernamePasswordCredentialsConverter;
    }

    @Override
    public CredentialsDTO toDTO( Credentials credentials ) {
        switch( credentials.getType() ){
            case USERNAME_PASSWORD:
                return usernamePasswordCredentialsConverter.toDTO( ( UsernamePasswordCredentials ) credentials );
            default:
                return null;
        }
    }

    @Override
    public Credentials toDomain( CredentialsDTO dto ) {
        switch( dto.type ){
            case USERNAME_PASSWORD:
                return usernamePasswordCredentialsConverter.toDomain( ( UsernamePasswordCredentialsDTO ) dto );
            default:
                return null;
        }
    }
}
