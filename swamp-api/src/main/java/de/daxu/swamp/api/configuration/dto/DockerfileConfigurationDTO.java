package de.daxu.swamp.api.configuration.dto;

import org.hibernate.validator.constraints.NotBlank;

public class DockerfileConfigurationDTO extends RunConfigurationDTO {

    @NotBlank(message = "{NotBlank.DockerfileConfigurationDTO.dockerfile}")
    public String dockerfile;

}
