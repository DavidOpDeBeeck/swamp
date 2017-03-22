package de.daxu.swamp.api.continent.dto;

import de.daxu.swamp.api.location.dto.LocationCreateDTO;

public class ContinentCreateDTO extends LocationCreateDTO {

    @SuppressWarnings("unused")
    private ContinentCreateDTO() {
        super();
    }

    private ContinentCreateDTO(String name) {
        super(name);
    }

    public static class Builder extends LocationCreateDTO.Builder<Builder> {

        public ContinentCreateDTO build() {
            return new ContinentCreateDTO(name);
        }
    }
}
