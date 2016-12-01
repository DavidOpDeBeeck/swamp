package de.daxu.swamp.scheduling.write.serverinstance;


import de.daxu.swamp.common.cqrs.EntityId;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@AttributeOverride( name = "value", column = @Column( name = "server_instance_id" ) )
public class ServerInstanceId extends EntityId {

    private ServerInstanceId() {
    }

    private ServerInstanceId( UUID serverInstanceId ) {
        super( serverInstanceId );
    }

    public static ServerInstanceId random() {
        return new ServerInstanceId( UUID.randomUUID() );
    }

    public static ServerInstanceId from( String id ) {
        return new ServerInstanceId( UUID.fromString( id ) );
    }
}
