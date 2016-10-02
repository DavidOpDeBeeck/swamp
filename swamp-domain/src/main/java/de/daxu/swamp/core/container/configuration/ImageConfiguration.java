package de.daxu.swamp.core.container.configuration;

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

    public ImageConfiguration() {
    }

    public ImageConfiguration( String name ) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println( "test" );
    }

    public String getName() {
        return name;
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
