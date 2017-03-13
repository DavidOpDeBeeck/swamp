package de.daxu.swamp.core.container;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.PortMappings.*;
import static de.daxu.swamp.core.container.PortMapping.Builder;

public class PortMappingTestBuilder {

    private Integer internal = INTERNAL_PORT;
    private Integer external = EXTERNAL_PORT;

    public static PortMappingTestBuilder aPortMapping() {
        return new PortMappingTestBuilder();
    }

    public static PortMappingTestBuilder anotherPortMapping() {
        return new PortMappingTestBuilder()
                .withInternal(ANOTHER_INTERNAL_PORT)
                .withExternal(ANOTHER_EXTERNAL_PORT);
    }

    public PortMappingTestBuilder withInternal(Integer internal) {
        this.internal = internal;
        return this;
    }

    public PortMappingTestBuilder withExternal(Integer external) {
        this.external = external;
        return this;
    }

    public PortMapping build() {
        return Builder.aPortMapping()
                .withInternal(internal)
                .withExternal(external)
                .build();
    }
}