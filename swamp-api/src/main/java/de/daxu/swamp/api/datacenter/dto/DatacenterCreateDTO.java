package de.daxu.swamp.api.datacenter.dto;

import de.daxu.swamp.api.location.dto.LocationCreateDTO;

public class DatacenterCreateDTO extends LocationCreateDTO {

    @SuppressWarnings("unused")
    private DatacenterCreateDTO() {
        super();
    }

    private DatacenterCreateDTO(String name) {
        super(name);
    }

    public static class Builder extends LocationCreateDTO.Builder<Builder> {

        public DatacenterCreateDTO build() {
            return new DatacenterCreateDTO(name);
        }
    }
}
