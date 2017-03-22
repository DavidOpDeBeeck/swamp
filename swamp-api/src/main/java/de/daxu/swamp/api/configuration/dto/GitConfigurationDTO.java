package de.daxu.swamp.api.configuration.dto;

import de.daxu.swamp.core.configuration.RunConfigurationType;
import org.hibernate.validator.constraints.NotBlank;

public class GitConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.GitConfigurationDTO.url}")
    private String url;
    @NotBlank(message = "{NotBlank.GitConfigurationDTO.branch}")
    private String branch;
    @NotBlank(message = "{NotBlank.GitConfigurationDTO.path}")
    private String path;
    //private final UsernamePasswordCredentialsDTO credentials;

    @SuppressWarnings("unused")
    private GitConfigurationDTO() {
        super(RunConfigurationType.GIT);
    }

    private GitConfigurationDTO(String url, String branch, String path) {
        this();
        this.url = url;
        this.branch = branch;
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public String getBranch() {
        return branch;
    }

    public String getPath() {
        return path;
    }

    public static class Builder {

        private String url;
        private String branch;
        private String path;

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withBranch(String branch) {
            this.branch = branch;
            return this;
        }

        public Builder withPath(String path) {
            this.path = path;
            return this;
        }

        public GitConfigurationDTO build() {
            return new GitConfigurationDTO(url, branch, path);
        }
    }
}
