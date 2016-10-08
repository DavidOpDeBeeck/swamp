package de.daxu.swamp.core.container.configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "image_configuration" )
@DiscriminatorValue( "image" )
public class ImageConfiguration extends RunConfiguration {

    @NotBlank( message = "{NotBlank.ImageConfiguration.name}" )
    @Column( name = "name" )
    private String name;

    public ImageConfiguration() {
    }

    public ImageConfiguration( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public List<String> execute( DockerClient client ) {
        CreateContainerResponse response = client.createContainerCmd( name ).exec();
        return new ArrayList<String>() {{
            add( response.getId() );
        }};
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.IMAGE;
    }

    public static class ImageConfigurationBuilder {

        private String name;

        public static ImageConfigurationBuilder imageConfigurationBuilder() {
            return new ImageConfigurationBuilder();
        }

        public ImageConfigurationBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public ImageConfiguration build() {
            return new ImageConfiguration( name );
        }
    }
}
