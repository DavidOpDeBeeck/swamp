package de.daxu.swamp.core.configuration;

import de.daxu.swamp.core.credentials.UsernamePasswordCredentials;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.*;
import static de.daxu.swamp.core.configuration.GitConfiguration.Builder;
import static de.daxu.swamp.core.credentials.UsernamePasswordCredentialsTestBuilder.anUsernamePasswordCredentials;
import static de.daxu.swamp.core.credentials.UsernamePasswordCredentialsTestBuilder.anotherUsernamePasswordCredentials;

public class GitConfigurationTestBuilder {

    private String url = GIT_URL;
    private String branch = GIT_BRANCH;
    private String path = GIT_PATH;
    private UsernamePasswordCredentials credentials = anUsernamePasswordCredentials().build();

    public static GitConfigurationTestBuilder aGitConfiguration() {
        return new GitConfigurationTestBuilder();
    }

    public static GitConfigurationTestBuilder anotherGitConfiguration() {
        return new GitConfigurationTestBuilder()
                .withUrl(ANOTHER_GIT_URL)
                .withBranch(ANOTHER_GIT_BRANCH)
                .withPath(ANOTHER_GIT_PATH)
                .withCredentials(anotherUsernamePasswordCredentials().build());
    }

    public GitConfigurationTestBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public GitConfigurationTestBuilder withBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public GitConfigurationTestBuilder withPath(String path) {
        this.path = path;
        return this;
    }

    public GitConfigurationTestBuilder withCredentials(UsernamePasswordCredentials credentials) {
        this.credentials = credentials;
        return this;
    }

    public GitConfiguration build() {
        return Builder.aGitConfiguration()
                .withUrl(url)
                .withBranch(branch)
                .withPath(path)
                .withCredentials(credentials)
                .build();
    }
}