package de.daxu.swamp.api.credentials.converter;

import de.daxu.swamp.api.credentials.dto.UsernamePasswordCredentialsDTO;
import de.daxu.swamp.common.aes.AES;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.credentials.UsernamePasswordCredentials.Builder.anUsernamePasswordCredentials;

@Component
public class UsernamePasswordCredentialsConverter
        implements DomainConverter<UsernamePasswordCredentialsDTO, UsernamePasswordCredentials>,
        DTOConverter<UsernamePasswordCredentials, UsernamePasswordCredentialsDTO> {

    private final AES aes;

    @Autowired
    public UsernamePasswordCredentialsConverter(AES aes) {
        this.aes = aes;
    }

    @Override
    public UsernamePasswordCredentialsDTO toDTO(UsernamePasswordCredentials credentials) {
        return new UsernamePasswordCredentialsDTO.Builder()
                .withUsername(credentials.getUsername())
                .withPassword(aes.decrypt(credentials.getPassword())) // todo: remove this
                .build();
    }

    @Override
    public UsernamePasswordCredentials toDomain(UsernamePasswordCredentialsDTO dto) {
        return anUsernamePasswordCredentials()
                .withUsername(dto.getUsername())
                .withPassword(aes.encrypt(dto.getPassword()))
                .build();
    }
}
