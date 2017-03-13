package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.datacenter.Datacenter.Builder;
import de.daxu.swamp.core.server.Server;

import java.util.HashSet;
import java.util.Set;

import static de.daxu.swamp.core.LocationTestConstants.Datacenters.*;

public class DatacenterTestBuilder {

    private String id;
    private String name = DATACENTER_NAME;
    private Set<Server> servers = DATACENTER_SERVERS;

    public static DatacenterTestBuilder aDatacenter() {
        return new DatacenterTestBuilder();
    }

    public static DatacenterTestBuilder anotherDatacenter() {
        return new DatacenterTestBuilder()
                .withName(ANOTHER_DATACENTER_NAME)
                .withServers(ANOTHER_DATACENTER_SERVERS);
    }

    public DatacenterTestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public DatacenterTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DatacenterTestBuilder withServers(Set<Server> servers) {
        this.servers = servers;
        return this;
    }

    public Datacenter build() {
        return Builder.aDatacenter()
                .withId(id)
                .withName(name)
                .withServers(new HashSet<>(servers))
                .build();
    }
}