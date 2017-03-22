package de.daxu.swamp.api.containertemplate.dto;

import de.daxu.swamp.common.ValueObject;

public class PortMappingDTO extends ValueObject {

    private Integer internal;
    private Integer external;

    @SuppressWarnings("unused")
    private PortMappingDTO() {
    }

    private PortMappingDTO(Integer internal, Integer external) {
        this.internal = internal;
        this.external = external;
    }

    public Integer getInternal() {
        return internal;
    }

    public Integer getExternal() {
        return external;
    }

    public static class Builder {

        private Integer internal;
        private Integer external;

        public Builder withInternal(Integer internal) {
            this.internal = internal;
            return this;
        }

        public Builder withExternal(Integer external) {
            this.external = external;
            return this;
        }

        public PortMappingDTO build() {
            return new PortMappingDTO(internal, external);
        }
    }
}
