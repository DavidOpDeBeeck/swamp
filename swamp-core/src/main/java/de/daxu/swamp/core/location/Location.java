package de.daxu.swamp.core.location;

import de.daxu.swamp.common.Identifiable;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table( name = "location" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class Location extends Identifiable {

    @NotBlank( message = "{NotBlank.Location.name}" )
    @Column( name = "name", unique = true )
    private String name;

    Location() {
    }

    Location( String id, String name ) {
        this.id = id;
        this.name = name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract LocationType getType();

    public static abstract class LocationBuilder<B extends LocationBuilder<B>> {

        protected String id;
        protected String name;

        public B withId( String id ) {
            this.id = id;
            return ( B ) this;
        }

        public B withName( String name ) {
            this.name = name;
            return ( B ) this;
        }

        public abstract Location build();
    }
}
