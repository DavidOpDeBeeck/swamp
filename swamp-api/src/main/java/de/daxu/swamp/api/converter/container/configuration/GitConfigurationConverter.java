package de.daxu.swamp.api.converter.container.configuration;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.configuration.GitConfigurationDTO;
import de.daxu.swamp.core.container.configuration.GitConfiguration;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.container.configuration.GitConfiguration.GitConfigurationBuilder.aGitConfiguration;

@Component
public class GitConfigurationConverter implements DTOConverter<GitConfiguration, GitConfigurationDTO>, DomainConverter<GitConfigurationDTO, GitConfiguration> {

    @Override
    public GitConfigurationDTO toDTO( GitConfiguration configuration ) {
        GitConfigurationDTO dto = new GitConfigurationDTO();
        dto.url = configuration.getUrl();
        dto.branch = configuration.getBranch();
        dto.path = configuration.getPath();
        dto.username = configuration.getUsername();
        dto.password = configuration.getPassword();
        dto.type = configuration.getType();
        return dto;
    }

    @Override
    public GitConfiguration toDomain( GitConfigurationDTO dto ) {
        return aGitConfiguration()
                .withUrl( dto.url )
                .withBranch( dto.branch )
                .withPath( dto.path )
                .withUsername( dto.username )
                .withPassword( dto.password )
                .build();
    }
}