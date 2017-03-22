package de.daxu.swamp.api.continent.dto;

import de.daxu.swamp.api.location.dto.LocationDTO;

import static de.daxu.swamp.core.location.LocationType.CONTINENT;

public class ContinentDTO extends LocationDTO {

    private int datacenters;

    @SuppressWarnings("unused")
    public ContinentDTO() {
    }

    private ContinentDTO(String id, String name, int datacenters) {
        super(id, name, CONTINENT);
        this.datacenters = datacenters;
    }

    public static class Builder extends LocationDTO.Builder<Builder> {

        private int datacenters;

        public Builder withDatacenters(int datacenters) {
            this.datacenters = datacenters;
            return this;
        }

        public ContinentDTO build() {
            return new ContinentDTO(id, name, datacenters);
        }
    }
}
