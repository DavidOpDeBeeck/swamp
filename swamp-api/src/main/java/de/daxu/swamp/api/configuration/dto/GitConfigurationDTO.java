package de.daxu.swamp.api.configuration.dto;

import de.daxu.swamp.api.credentials.dto.UsernamePasswordCredentialsDTO;

public class GitConfigurationDTO extends RunConfigurationDTO {

    public String url;
    public String branch;
    public String path;
    public UsernamePasswordCredentialsDTO credentials;

}
