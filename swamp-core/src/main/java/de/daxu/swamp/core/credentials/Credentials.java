package de.daxu.swamp.core.credentials;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.*;

@Entity
@Table( name = "credentials" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class Credentials extends Identifiable {

    public abstract CredentialsType getType();
}
