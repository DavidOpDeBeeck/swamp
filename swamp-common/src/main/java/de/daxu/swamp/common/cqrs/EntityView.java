package de.daxu.swamp.common.cqrs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.daxu.swamp.common.jpa.Identifiable;

import javax.persistence.Entity;

@Entity
public abstract class EntityView extends Identifiable {

    @JsonIgnore
    public String getId() {
        return id;
    }
}
