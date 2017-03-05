package de.daxu.swamp.api.configuration.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ImageConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.ImageConfigurationDTO.name}")
    public String name;

}
