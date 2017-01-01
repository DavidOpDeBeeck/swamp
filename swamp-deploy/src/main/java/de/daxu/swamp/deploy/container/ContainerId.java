package de.daxu.swamp.deploy.container;

import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride( name = "value", column = @Column( name = "container_id" ) )
public class ContainerId extends EntityId {

    public static ContainerId of( String id ) {
        return new ContainerId( UUID.fromString( id ) );
    }

    @SuppressWarnings( "unused" )
    private ContainerId() {
    }

    private ContainerId( UUID containerId ) {
        super( containerId );
    }
}
