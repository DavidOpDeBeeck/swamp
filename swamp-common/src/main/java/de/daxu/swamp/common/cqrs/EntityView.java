package de.daxu.swamp.common.cqrs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.daxu.swamp.common.Identifiable;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@JsonIgnoreProperties( value = "id" )
public abstract class EntityView extends Identifiable {

}
