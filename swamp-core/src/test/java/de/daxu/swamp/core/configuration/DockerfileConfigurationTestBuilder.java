package de.daxu.swamp.core.configuration;

import de.daxu.swamp.core.configuration.DockerfileConfiguration.Builder;

public class DockerfileConfigurationTestBuilder {

    private String dockerfile;

    public static DockerfileConfigurationTestBuilder aDockerfileConfiguration() {
        return new DockerfileConfigurationTestBuilder();
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