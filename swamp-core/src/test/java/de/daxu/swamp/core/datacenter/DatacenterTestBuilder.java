package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.datacenter.Datacenter.Builder;
import de.daxu.swamp.core.server.Server;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DatacenterTestBuilder {

    private String id;
    private String name = "a datacenter name";
    private Set<Server> servers = newHashSet();

    public static DatacenterTestBuilder aDatacenter() {
        return new DatacenterTestBuilder();
    }

    public static DatacenterTestBuilder anotherDatacenter() {
        return new DatacenterTestBuilder()
                .withName( "another datacenter name" );
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
        return Builder.aDatacenter()
                .withId( id )
                .withName( name )
                .withServers( servers )
                .build();
    }
}