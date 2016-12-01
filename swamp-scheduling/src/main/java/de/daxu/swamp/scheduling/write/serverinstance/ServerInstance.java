package de.daxu.swamp.scheduling.write.serverinstance;

import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;

@SuppressWarnings( "unused" )
public class ServerInstance extends AbstractAnnotatedAggregateRoot<ServerInstanceId> {

    @AggregateIdentifier
    private ServerInstanceId serverInstanceId;

    ServerInstance() {
    }


}
