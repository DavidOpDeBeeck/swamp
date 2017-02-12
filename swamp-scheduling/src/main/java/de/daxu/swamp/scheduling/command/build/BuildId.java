package de.daxu.swamp.scheduling.command.build;


import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride( name = "value", column = @Column( name = "build_id" ) )
public class BuildId extends EntityId {

    private BuildId() {
    }

    private BuildId(UUID projectInstanceId ) {
        super( projectInstanceId );
    }

    public static BuildId random() {
        return new BuildId( UUID.randomUUID() );
    }

    public static BuildId from(String id ) {
        return new BuildId( UUID.fromString( id ) );
    }
}
