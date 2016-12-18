package de.daxu.swamp.core.configuration;

public class ImageConfigurationTestBuilder {

    private String name = "an image name";

    public static ImageConfigurationTestBuilder anImageConfigurationTestBuilder() {
        return new ImageConfigurationTestBuilder();
    }

    public ImageConfigurationTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ImageConfiguration build() {
        return new ImageConfiguration( name );
    }
}