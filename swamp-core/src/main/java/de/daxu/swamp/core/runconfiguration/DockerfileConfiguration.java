package de.daxu.swamp.core.runconfiguration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
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
    public CreateContainerCmd execute( DockerClient client ) {
        return null;
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.DOCKERFILE;
    }

    public static class DockerfileConfigurationBuilder {

        private String dockerfile;

        public static DockerfileConfigurationBuilder aDockerfileConfiguration() {
            return new DockerfileConfigurationBuilder();
        }

        public DockerfileConfigurationBuilder withDockerfile( String dockerfile ) {
            this.dockerfile = dockerfile;
            return this;
        }

        public DockerfileConfiguration build() {
            return new DockerfileConfiguration( dockerfile );
        }
    }
}
