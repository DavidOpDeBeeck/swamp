package de.daxu.swamp.core.container.configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "git_configuration" )
@DiscriminatorValue( "git" )
public class GitConfiguration extends RunConfiguration {

    @NotBlank( message = "{NotBlank.GitConfiguration.url}" )
    @Column( name = "url" )
    private String url;

    @NotBlank( message = "{NotBlank.GitConfiguration.branch}" )
    @Column( name = "branch" )
    private String branch;

    @NotBlank( message = "{NotBlank.GitConfiguration.path}" )
    @Column( name = "path" )
    private String path;

    @Column( name = "username" )
    private String username; // TODO: replace with credentials alpha v0.3?

    @Column( name = "password" )
    private String password; // TODO: replace with credentials alpha v0.3?

    private GitConfiguration() {
    }

    private GitConfiguration( String url, String branch, String path, String username, String password ) {
        this.url = url;
        this.branch = branch;
        this.path = path;
        this.username = username;
        this.password = password;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public CreateContainerCmd execute( DockerClient client ) {
        return null;
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.GIT;
    }

    public static class GitConfigurationBuilder {

        private String url;
        private String branch;
        private String path;
        private String username; // TODO: replace with credentials alpha v0.3?
        private String password; // TODO: replace with credentials alpha v0.3?

        public static GitConfigurationBuilder aGitConfiguration() {
            return new GitConfigurationBuilder();
        }

        public GitConfigurationBuilder withUrl( String url ) {
            this.url = url;
            return this;
        }

        public GitConfigurationBuilder withBranch( String branch ) {
            this.branch = branch;
            return this;
        }

        public GitConfigurationBuilder withPath( String path ) {
            this.path = path;
            return this;
        }

        public GitConfigurationBuilder withUsername( String username ) {
            this.username = username;
            return this;
        }

        public GitConfigurationBuilder withPassword( String password ) {
            this.password = password;
            return this;
        }

        public GitConfiguration build() {
            return new GitConfiguration( url, branch, path, username, password );
        }
    }
}
