package de.daxu.swamp.core.configuration;

public class DockerfileConfigurationTestBuilder {

    private String dockerfile;

    public static DockerfileConfigurationTestBuilder aDockerfileConfigurationTestBuilder() {
        return new DockerfileConfigurationTestBuilder();
    }

    public DockerfileConfigurationTestBuilder withDockerfile( String dockerfile ) {
        this.dockerfile = dockerfile;
        return this;
    }

    public DockerfileConfiguration build() {
        return new DockerfileConfiguration( dockerfile );
    }
}