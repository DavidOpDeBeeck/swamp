package de.daxu.swamp.core.configuration;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.ANOTHER_IMAGE_NAME;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.IMAGE_NAME;
import static de.daxu.swamp.core.configuration.ImageConfiguration.Builder;

public class ImageConfigurationTestBuilder {

    private String name = IMAGE_NAME;

    public static ImageConfigurationTestBuilder anImageConfiguration() {
        return new ImageConfigurationTestBuilder();
    }

    public static ImageConfigurationTestBuilder anotherImageConfiguration() {
        return new ImageConfigurationTestBuilder()
                .withName(ANOTHER_IMAGE_NAME);
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