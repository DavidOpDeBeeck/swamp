package de.daxu.swamp.api.datacenter.dto;

import de.daxu.swamp.api.location.dto.LocationDTO;

import static de.daxu.swamp.core.location.LocationType.DATACENTER;

public class DatacenterDTO extends LocationDTO {

    private int servers;

    @SuppressWarnings("unused")
    private DatacenterDTO() {
    }

    private DatacenterDTO(String id, String name, int servers) {
        super(id, name, DATACENTER);
        this.servers = servers;
    }

    public static class Builder extends LocationDTO.Builder<Builder> {

        private int servers;

        public Builder withDatacenters(int servers) {
            this.servers = servers;
            return this;
        }

        public DatacenterDTO build() {
            return new DatacenterDTO(id, name, servers);
        }
    }
}
