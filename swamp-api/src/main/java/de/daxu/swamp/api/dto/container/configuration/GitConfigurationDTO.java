package de.daxu.swamp.api.dto.container.configuration;

public class GitConfigurationDTO extends RunConfigurationDTO {

    public String url;
    public String branch;
    public String path;
    public String username; // TODO: replace with credentials alpha v0.3?
    public String password; // TODO: replace with credentials alpha v0.3?

}
