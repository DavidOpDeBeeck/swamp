package de.daxu.swamp.core.location.datacenter;

import com.google.common.collect.Sets;
import de.daxu.swamp.core.location.server.Server;

import java.util.Set;

import static de.daxu.swamp.core.location.server.ServerBuilderTestBuilder.aServerBuilderTestBuilder;

public class DatacenterTestBuilder {

    private String id = "a datacenter id";
    private String name  = "a datacenter name";
    private Set<Server> servers = Sets.newHashSet( aServerBuilderTestBuilder().build() );

    public static DatacenterTestBuilder aDatacenterTestBuilder() {
        return new DatacenterTestBuilder();
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