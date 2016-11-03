package de.daxu.swamp.scheduling.write.containerinstance;


import de.daxu.swamp.common.cqrs.EntityId;

import java.util.UUID;

public class ContainerInstanceId extends EntityId {

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
