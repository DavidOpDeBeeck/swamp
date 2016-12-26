package de.daxu.swamp.scheduling.command.projectinstance;


import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride( name = "value", column = @Column( name = "project_instance_id" ) )
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
