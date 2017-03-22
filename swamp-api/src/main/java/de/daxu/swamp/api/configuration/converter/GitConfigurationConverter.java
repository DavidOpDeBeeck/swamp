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
    public GitConfigurationConverter(UsernamePasswordCredentialsConverter usernamePasswordCredentialsConverter) {
        this.usernamePasswordCredentialsConverter = usernamePasswordCredentialsConverter;
    }

    @Override
    public GitConfigurationDTO toDTO(GitConfiguration configuration) {
        return new GitConfigurationDTO.Builder()
                .withUrl(configuration.getUrl())
                .withBranch(configuration.getBranch())
                .withPath(configuration.getPath())
                .build();
    }

    @Override
    public GitConfiguration toDomain(GitConfigurationDTO dto) {
        return aGitConfiguration()
                .withUrl(dto.getUrl())
                .withBranch(dto.getBranch())
                .withPath(dto.getPath())
                //.withCredentials( usernamePasswordCredentialsConverter.toDomain( dto.credentials ) )
                .build();
    }
}