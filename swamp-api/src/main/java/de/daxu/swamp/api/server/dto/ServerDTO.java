package de.daxu.swamp.api.server.dto;

import de.daxu.swamp.api.location.dto.LocationDTO;

import static de.daxu.swamp.core.location.LocationType.SERVER;

public class ServerDTO extends LocationDTO {

    private String ip;

    @SuppressWarnings("unused")
    private ServerDTO() {
    }

    private ServerDTO(String id, String name, String ip) {
        super(id, name, SERVER);
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public static class Builder extends LocationDTO.Builder<Builder> {

        private String ip;

        public Builder withIp(String ip) {
            this.ip = ip;
            return this;
        }

        public ServerDTO build() {
            return new ServerDTO(id, name, ip);
        }
    }
}
