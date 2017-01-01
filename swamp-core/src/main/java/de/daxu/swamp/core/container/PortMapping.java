package de.daxu.swamp.core.container;

import de.daxu.swamp.common.jpa.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "port_mapping" )
public class PortMapping extends Identifiable {

    @NotNull( message = "{NotNull.PortMapping.internal}" )
    @Column( name = "internal" )
    private Integer internal;

    @NotNull( message = "{NotNull.PortMapping.external}" )
    @Column( name = "external" )
    private Integer external;

    private PortMapping() {
    }

    PortMapping( Integer internal, Integer external ) {
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

        public static Builder aPortMapping() {
            return new Builder();
        }

        public Builder withInternal( Integer internal ) {
            this.internal = internal;
            return this;
        }

        public Builder withExternal( Integer external ) {
            this.external = external;
            return this;
        }

        public PortMapping build() {
            return new PortMapping( internal, external );
        }
    }
}
