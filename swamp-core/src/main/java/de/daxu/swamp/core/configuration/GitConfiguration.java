package de.daxu.swamp.core.configuration;

import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "git_configuration")
@DiscriminatorValue("git")
public class GitConfiguration extends RunConfiguration {

    @NotBlank(message = "{NotBlank.GitConfiguration.url}")
    @Column(name = "url")
    private String url;

    @NotBlank(message = "{NotBlank.GitConfiguration.branch}")
    @Column(name = "branch")
    private String branch;

    @NotBlank(message = "{NotBlank.GitConfiguration.path}")
    @Column(name = "path")
    private String path;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credentials_id")
    private UsernamePasswordCredentials credentials;

    private GitConfiguration() {
    }

    private GitConfiguration(String url, String branch, String path, UsernamePasswordCredentials credentials) {
        this.url = url;
        this.branch = branch;
        this.path = path;
        this.credentials = credentials;
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

    public UsernamePasswordCredentials getCredentials() {
        return credentials;
    }

    @Override
    public <T> T configure(RunConfigurator<T> configurator) {
        return configurator.configure(this);
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.GIT;
    }

    public static class Builder {

        private String url;
        private String branch;
        private String path;
        private UsernamePasswordCredentials credentials;

        public static Builder aGitConfiguration() {
            return new Builder();
        }

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

        public Builder withCredentials(UsernamePasswordCredentials credentials) {
            this.credentials = credentials;
            return this;
        }

        public GitConfiguration build() {
            return new GitConfiguration(url, branch, path, credentials);
        }
    }
}
