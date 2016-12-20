package de.daxu.swamp.api.configuration.dto;

import de.daxu.swamp.core.configuration.RunConfigurationType;

public class ImageConfigurationDTOTestBuilder {
    private String name = "an image name";

    public static ImageConfigurationDTOTestBuilder anImageConfigurationDTOTestBuilder() {
        return new ImageConfigurationDTOTestBuilder();
    }

    public ImageConfigurationDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ImageConfigurationDTO build() {
        ImageConfigurationDTO dto = new ImageConfigurationDTO();
        dto.name = this.name;
        dto.type = RunConfigurationType.IMAGE;
        return dto;
    }
}