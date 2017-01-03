package de.daxu.swamp.core.configuration;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "dockerfile_configuration" )
@DiscriminatorValue( "dockerfile" )
public class DockerfileConfiguration extends RunConfiguration {

    @NotBlank( message = "{NotBlank.DockerfileConfiguration.dockerfile}" )
    @Column( name = "dockerfile" )
    private String dockerfile;

    private DockerfileConfiguration() {
    }

    private DockerfileConfiguration( String dockerfile ) {
        this.dockerfile = dockerfile;
    }

    public String getDockerfile() {
        return dockerfile;
    }

    @Override
    public <T> T configure( RunConfigurator<T> configurator ) {
        return configurator.configure( this );
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.DOCKERFILE;
    }

    public static class Builder {

        private String dockerfile;

        public static Builder aDockerfileConfiguration() {
            return new Builder();
        }

        public Builder withDockerfile( String dockerfile ) {
            this.dockerfile = dockerfile;
            return this;
        }

        public DockerfileConfiguration build() {
            return new DockerfileConfiguration( dockerfile );
        }
    }
}
