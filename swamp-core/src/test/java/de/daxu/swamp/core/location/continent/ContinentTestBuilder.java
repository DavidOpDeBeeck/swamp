package de.daxu.swamp.core.location.continent;

import de.daxu.swamp.core.location.datacenter.Datacenter;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.location.datacenter.DatacenterTestBuilder.aDatacenterTestBuilder;

public class ContinentTestBuilder {

    private String id = "a continent id";
    private String name  = "a continent name";
    private Set<Datacenter> datacenters = newHashSet( aDatacenterTestBuilder().build() );

    public static ContinentTestBuilder aContinentTestBuilder() {
        return new ContinentTestBuilder();
    }

    public ContinentTestBuilder withId( String id ) {
        this.id = id;
        return this;
    }

    public ContinentTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ContinentTestBuilder withDatacenters( Set<Datacenter> datacenters ) {
        this.datacenters = datacenters;
        return this;
    }

    public Continent build() {
        return new Continent( id, name, datacenters );
    }
}