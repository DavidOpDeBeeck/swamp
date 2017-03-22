package de.daxu.swamp.api.configuration.dto;

import org.hibernate.validator.constraints.NotBlank;

import static de.daxu.swamp.core.configuration.RunConfigurationType.IMAGE;

public class ImageConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.ImageConfigurationDTO.name}")
    private String name;

    @SuppressWarnings("unused")
    private ImageConfigurationDTO() {
        super(IMAGE);
    }

    private ImageConfigurationDTO(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public ImageConfigurationDTO build() {
            return new ImageConfigurationDTO(name);
        }
    }
}
