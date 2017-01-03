package de.daxu.swamp.core.configuration;

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

    private ImageConfiguration( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T configure( RunConfigurator<T> configurator ) {
        return configurator.configure( this );
    }

    @Override
    public RunConfigurationType getType() {
        return RunConfigurationType.IMAGE;
    }

    public static class Builder {

        private String name;

        public static Builder anImageConfiguration() {
            return new Builder();
        }

        public Builder withName( String name ) {
            this.name = name;
            return this;
        }

        public ImageConfiguration build() {
            return new ImageConfiguration( name );
        }
    }
}
