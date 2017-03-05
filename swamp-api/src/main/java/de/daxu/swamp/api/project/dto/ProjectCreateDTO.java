package de.daxu.swamp.api.project.dto;

import org.hibernate.validator.constraints.NotBlank;

public class ProjectCreateDTO {

    @NotBlank(message = "{NotBlank.ProjectCreateDTO.name}")
    public String name;
    @NotBlank(message = "{NotBlank.ProjectCreateDTO.description}")
    public String description;

}
