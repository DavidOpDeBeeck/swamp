package de.daxu.swamp.core.configuration;

import de.daxu.swamp.core.configuration.DockerfileConfiguration.Builder;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.ANOTHER_DOCKER_FILE;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.DOCKER_FILE;

public class DockerfileConfigurationTestBuilder {

    private String dockerfile = DOCKER_FILE;

    public static DockerfileConfigurationTestBuilder aDockerfileConfiguration() {
        return new DockerfileConfigurationTestBuilder();
    }

    public static DockerfileConfigurationTestBuilder anotherDockerfileConfiguration() {
        return new DockerfileConfigurationTestBuilder()
                .withDockerfile(ANOTHER_DOCKER_FILE);
    }

    public DockerfileConfigurationTestBuilder withDockerfile( String dockerfile ) {
        this.dockerfile = dockerfile;
        return this;
    }

    public DockerfileConfiguration build() {
        return Builder.aDockerfileConfiguration()
                .withDockerfile( dockerfile )
                .build();
    }
}