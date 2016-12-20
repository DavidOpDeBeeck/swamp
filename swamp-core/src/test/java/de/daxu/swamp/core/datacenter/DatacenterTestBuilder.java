package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.server.Server;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.server.ServerBuilderTestBuilder.aServerBuilderTestBuilder;
import static de.daxu.swamp.core.server.ServerBuilderTestBuilder.anotherServerBuilderTestBuilder;

public class DatacenterTestBuilder {

    private String id;
    private String name  = "a datacenter name";
    private Set<Server> servers = newHashSet( aServerBuilderTestBuilder().build() );

    public static DatacenterTestBuilder aDatacenterTestBuilder() {
        return new DatacenterTestBuilder();
    }

    public static DatacenterTestBuilder anotherDatacenterTestBuilder() {
        return new DatacenterTestBuilder()
                .withName( "another datacenter name" )
                .withServers( newHashSet( anotherServerBuilderTestBuilder().build() ) );
    }

    public DatacenterTestBuilder withId( String id ) {
        this.id = id;
        return this;
    }

    public DatacenterTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public DatacenterTestBuilder withServers( Set<Server> servers ) {
        this.servers = servers;
        return this;
    }

    public Datacenter build() {
        return new Datacenter( id, name, servers );
    }
}