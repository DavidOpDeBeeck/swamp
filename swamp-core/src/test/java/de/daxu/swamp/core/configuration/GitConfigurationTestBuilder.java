package de.daxu.swamp.core.configuration;

import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;

import static de.daxu.swamp.core.configuration.GitConfiguration.Builder;
import static de.daxu.swamp.core.credentials.UsernamePasswordCredentialsTestBuilder.anUsernamePasswordCredentials;

public class GitConfigurationTestBuilder {

    private String url = "a git url";
    private String branch = "a git branch";
    private String path = "a git path";
    private UsernamePasswordCredentials credentials = anUsernamePasswordCredentials().build();

    public static GitConfigurationTestBuilder aGitConfiguration() {
        return new GitConfigurationTestBuilder();
    }

    public GitConfigurationTestBuilder withUrl( String url ) {
        this.url = url;
        return this;
    }

    public GitConfigurationTestBuilder withBranch( String branch ) {
        this.branch = branch;
        return this;
    }

    public GitConfigurationTestBuilder withPath( String path ) {
        this.path = path;
        return this;
    }

    public GitConfigurationTestBuilder withCredentials( UsernamePasswordCredentials credentials ) {
        this.credentials = credentials;
        return this;
    }

    public GitConfiguration build() {
        return Builder.aGitConfiguration()
                .withUrl( url )
                .withBranch( branch )
                .withPath( path )
                .withCredentials( credentials )
                .build();
    }
}