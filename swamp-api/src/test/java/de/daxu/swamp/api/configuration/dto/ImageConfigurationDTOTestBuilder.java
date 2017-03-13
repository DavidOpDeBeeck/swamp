package de.daxu.swamp.api.configuration.dto;

import de.daxu.swamp.core.configuration.RunConfigurationType;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.RunConfigurations.IMAGE_NAME;

public class ImageConfigurationDTOTestBuilder {

    private String name = IMAGE_NAME;

    public static ImageConfigurationDTOTestBuilder anImageConfigurationDTO() {
        return new ImageConfigurationDTOTestBuilder();
    }

    public ImageConfigurationDTOTestBuilder withName(String name) {
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