package de.daxu.swamp.api.container.dto;

public class PortMappingDTOTestBuilder {
    private Integer internal = 8080;
    private Integer external = 8888;

    public static PortMappingDTOTestBuilder aPortMappingDTOTestBuilder() {
        return new PortMappingDTOTestBuilder();
    }

    public PortMappingDTOTestBuilder withInternal( Integer internal ) {
        this.internal = internal;
        return this;
    }

    public PortMappingDTOTestBuilder withExternal( Integer external ) {
        this.external = external;
        return this;
    }

    public PortMappingDTO build() {
        PortMappingDTO dto = new PortMappingDTO();
        dto.internal = this.internal;
        dto.external = this.external;
        return dto;
    }
}