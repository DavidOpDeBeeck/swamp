package de.daxu.swamp.api.dto.container.configuration;

import de.daxu.swamp.api.dto.credentials.UsernamePasswordCredentialsDTO;

public class GitConfigurationDTO extends RunConfigurationDTO {

    public String url;
    public String branch;
    public String path;
    public UsernamePasswordCredentialsDTO credentials;

}
