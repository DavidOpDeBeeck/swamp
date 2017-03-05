package de.daxu.swamp.api.configuration.dto;

import org.hibernate.validator.constraints.NotBlank;

public class GitConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.GitConfigurationDTO.url}")
    public String url;
    @NotBlank(message = "{NotBlank.GitConfigurationDTO.branch}")
    public String branch;
    @NotBlank(message = "{NotBlank.GitConfigurationDTO.path}")
    public String path;
    //public UsernamePasswordCredentialsDTO credentials;

}
