package de.daxu.swamp.api.container.dto;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.PortMappings.EXTERNAL_PORT;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.PortMappings.INTERNAL_PORT;

public class PortMappingDTOTestBuilder {
    private Integer internal = INTERNAL_PORT;
    private Integer external = EXTERNAL_PORT;

    public static PortMappingDTOTestBuilder aPortMappingDTO() {
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