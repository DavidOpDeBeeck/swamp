package de.daxu.swamp.core.configuration;

import static de.daxu.swamp.core.configuration.ImageConfiguration.Builder;

public class ImageConfigurationTestBuilder {

    private String name = "an image name";

    public static ImageConfigurationTestBuilder anImageConfiguration() {
        return new ImageConfigurationTestBuilder();
    }

    public ImageConfigurationTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ImageConfiguration build() {
        return Builder.anImageConfiguration()
                .withName( name )
                .build();
    }
}