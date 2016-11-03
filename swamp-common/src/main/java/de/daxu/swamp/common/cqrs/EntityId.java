package de.daxu.swamp.common.cqrs;

import java.util.UUID;

public abstract class EntityId implements AggregateId {

    private UUID value;

    protected EntityId( UUID value ) {
        this.value = value;
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
        return value.toString();
    }

    @Override
    public String toString() {
        return getValue();
    }
}
