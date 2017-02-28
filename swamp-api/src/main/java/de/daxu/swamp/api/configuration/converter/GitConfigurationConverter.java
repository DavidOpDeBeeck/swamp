package de.daxu.swamp.api.configuration.converter;

import de.daxu.swamp.api.configuration.dto.GitConfigurationDTO;
import de.daxu.swamp.api.credentials.converter.UsernamePasswordCredentialsConverter;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.configuration.GitConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.configuration.GitConfiguration.Builder.aGitConfiguration;

@Component
public class GitConfigurationConverter implements DTOConverter<GitConfiguration, GitConfigurationDTO>, DomainConverter<GitConfigurationDTO, GitConfiguration> {

    private final UsernamePasswordCredentialsConverter usernamePasswordCredentialsConverter;

    @Autowired
    public GitConfigurationConverter( UsernamePasswordCredentialsConverter usernamePasswordCredentialsConverter ) {
        this.usernamePasswordCredentialsConverter = usernamePasswordCredentialsConverter;
    }

    @Override
    public GitConfigurationDTO toDTO( GitConfiguration configuration ) {
        GitConfigurationDTO dto = new GitConfigurationDTO();
        dto.url = configuration.getUrl();
        dto.branch = configuration.getBranch();
        dto.path = configuration.getPath();
        //dto.credentials = usernamePasswordCredentialsConverter.toDTO( configuration.getCredentials() );
        dto.type = configuration.getType();
        return dto;
    }

    @Override
    public GitConfiguration toDomain( GitConfigurationDTO dto ) {
        return aGitConfiguration()
                .withUrl( dto.url )
                .withBranch( dto.branch )
                .withPath( dto.path )
                //.withCredentials( usernamePasswordCredentialsConverter.toDomain( dto.credentials ) )
                .build();
    }
}