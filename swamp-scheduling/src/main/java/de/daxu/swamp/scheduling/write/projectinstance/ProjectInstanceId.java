package de.daxu.swamp.scheduling.write.projectinstance;


import de.daxu.swamp.common.cqrs.EntityId;

import java.util.UUID;

public class ProjectInstanceId extends EntityId {

    private ProjectInstanceId( UUID projectInstanceId ) {
        super( projectInstanceId );
    }

    public static ProjectInstanceId random() {
        return new ProjectInstanceId( UUID.randomUUID() );
    }

    public static ProjectInstanceId from( String id ) {
        return new ProjectInstanceId( UUID.fromString( id ) );
    }
}
