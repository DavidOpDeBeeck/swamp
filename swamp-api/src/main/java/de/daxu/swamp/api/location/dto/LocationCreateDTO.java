package de.daxu.swamp.api.location.dto;

import de.daxu.swamp.common.ValueObject;
import org.hibernate.validator.constraints.NotBlank;

public abstract class LocationCreateDTO extends ValueObject {

    @NotBlank(message = "{NotBlank.LocationCreateDTO.name}")
    private String name;

    protected LocationCreateDTO() {
    }

    protected LocationCreateDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public static abstract class Builder<T extends Builder> {

        protected String name;

        public T withName(String name) {
            this.name = name;
            return (T) this;
        }

        public abstract LocationCreateDTO build();
    }
}
