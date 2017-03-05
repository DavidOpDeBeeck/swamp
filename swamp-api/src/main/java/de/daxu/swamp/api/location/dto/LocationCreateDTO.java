package de.daxu.swamp.api.location.dto;

import org.hibernate.validator.constraints.NotBlank;

public abstract class LocationCreateDTO {

    @NotBlank(message = "{NotBlank.LocationCreateDTO.name}")
    public String name;
}
