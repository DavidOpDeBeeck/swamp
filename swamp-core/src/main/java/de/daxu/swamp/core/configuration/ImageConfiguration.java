package de.daxu.swamp.core.configuration;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table( name = "image_configuration" )
@DiscriminatorValue( "image" )
public class ImageConfiguration extends RunConfiguration {

    @NotBlank( message = "{NotBlank.ImageConfiguration.name}" )
    @Column( name = "name" )
    private String name;

    private ImageConfiguration() {
    }

    ImageConfiguration( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public CreateContainerCmd execute( DockerClient client ) {
        return client.createContainerCmd( name );
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.IMAGE;
    }

    @Override
    public boolean equals( Object o ) {
        if( this == o ) return true;
        if( o == null || getClass() != o.getClass() ) return false;

        ImageConfiguration that = ( ImageConfiguration ) o;

        return name != null ? name.equals( that.name ) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + ( name != null ? name.hashCode() : 0 );
        return result;
    }

    public static class ImageConfigurationBuilder {

        private String name;

        public static ImageConfigurationBuilder anImageConfigurationBuilder() {
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
