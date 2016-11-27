package de.daxu.swamp.core.credentials;

import javax.persistence.*;

@Entity
@Table( name = "credentials" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "type" )
public abstract class Credentials {

    public abstract CredentialsType getType();
}
