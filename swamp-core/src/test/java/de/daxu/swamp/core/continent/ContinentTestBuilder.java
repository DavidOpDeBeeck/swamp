package de.daxu.swamp.core.continent;

import de.daxu.swamp.core.datacenter.Datacenter;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.datacenter.DatacenterTestBuilder.anotherDatacenterTestBuilder;

public class ContinentTestBuilder {

    private String id;
    private String name = "a continent name";
    private Set<Datacenter> datacenters = newHashSet();

    public static ContinentTestBuilder aContinentTestBuilder() {
        return new ContinentTestBuilder();
    }

    public static ContinentTestBuilder anotherContinentTestBuilder() {
        return new ContinentTestBuilder()
                .withName( "another continent name" )
                .withDatacenters( newHashSet( anotherDatacenterTestBuilder().build() ) );
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