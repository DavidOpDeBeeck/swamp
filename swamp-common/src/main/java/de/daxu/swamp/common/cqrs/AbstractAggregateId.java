package de.daxu.swamp.common.cqrs;

import java.util.UUID;

public abstract class AbstractAggregateId implements AggregateId {

    private String value;

    protected AbstractAggregateId( UUID value ) {
        this.value = value.toString();
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;

        AbstractAggregateId that = ( AbstractAggregateId ) o;

        return toString() != null ? toString().equals( that.toString() ) : that.toString() == null;
    }

    @Override
    public int hashCode() {
        return toString() != null ? toString().hashCode() : 0;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
