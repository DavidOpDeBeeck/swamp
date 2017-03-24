package de.daxu.swamp.common.cqrs;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class EntityId implements Comparable<EntityId>, AggregateId {

    protected String value;

    protected EntityId() {
    }

    protected EntityId(UUID value) {
        this.value = value.toString();
    }

    protected EntityId(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityId that = (EntityId) o;

        return value != null ? value.equals(that.value) : that.value == null;
    }

    @Override
    public int compareTo(EntityId entity) {
        return entity.value.compareTo(value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }

    @JsonValue
    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }
}
