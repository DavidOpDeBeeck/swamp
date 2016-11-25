package de.daxu.swamp.scheduling.write.containerinstance;


import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride( name = "value", column = @Column( name = "container_instance_id" ) )
public class ContainerInstanceId extends EntityId {

    private ContainerInstanceId() {
    }

    private ContainerInstanceId( UUID containerInstanceId ) {
        super( containerInstanceId );
    }

    public static ContainerInstanceId random() {
        return new ContainerInstanceId( UUID.randomUUID() );
    }

    public static ContainerInstanceId from( String id ) {
        return new ContainerInstanceId( UUID.fromString( id ) );
    }
}
