package de.daxu.swamp.core.continent;

import de.daxu.swamp.core.continent.Continent.Builder;
import de.daxu.swamp.core.datacenter.Datacenter;

import java.util.HashSet;
import java.util.Set;

import static de.daxu.swamp.core.LocationTestConstants.Continents.*;

public class ContinentTestBuilder {

    private String id;
    private String name = CONTINENT_NAME;
    private Set<Datacenter> datacenters = CONTINENT_DATACENTERS;

    public static ContinentTestBuilder aContinent() {
        return new ContinentTestBuilder();
    }

    public static ContinentTestBuilder anotherContinent() {
        return new ContinentTestBuilder()
                .withName(ANOTHER_CONTINENT_NAME)
                .withDatacenters(ANOTHER_CONTINENT_DATACENTERS);
    }

    public ContinentTestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ContinentTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ContinentTestBuilder withDatacenters(Set<Datacenter> datacenters) {
        this.datacenters = datacenters;
        return this;
    }

    public Continent build() {
        return Builder.aContinent()
                .withId(id)
                .withName(name)
                .withDatacenters(new HashSet<>(datacenters))
                .build();
    }
}