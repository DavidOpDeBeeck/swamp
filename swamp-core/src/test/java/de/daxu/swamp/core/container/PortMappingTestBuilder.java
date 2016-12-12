package de.daxu.swamp.core.container;

public class PortMappingTestBuilder {
    private Integer internal = 8080;
    private Integer external = 8888;

    public static PortMappingTestBuilder aPortMappingTestBuilder() {
        return new PortMappingTestBuilder();
    }

    public PortMappingTestBuilder withInternal( Integer internal ) {
        this.internal = internal;
        return this;
    }

    public PortMappingTestBuilder withExternal( Integer external ) {
        this.external = external;
        return this;
    }

    public PortMapping build() {
        return new PortMapping( internal, external );
    }
}