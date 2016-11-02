package de.daxu.swamp.scheduling.write.containerinstance;


import de.daxu.swamp.common.cqrs.AbstractAggregateId;

import java.util.UUID;

public class ContainerInstanceId extends AbstractAggregateId {

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
