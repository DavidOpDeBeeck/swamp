package de.daxu.swamp.common.cqrs;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class EntityId implements AggregateId {

    protected String value;

    protected EntityId() {
    }

    protected EntityId( UUID value ) {
        this.value = value.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        EntityId that = ( EntityId ) o;

        return value != null ? value.equals( that.value ) : that.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
