package de.daxu.swamp.core.continent;

import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.LocationType;
import de.daxu.swamp.core.server.Server;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table( name = "continent" )
@DiscriminatorValue( "continent" )
@SuppressWarnings( "unused" )
public class Continent extends Location {

    @OneToMany( fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "continent_id", referencedColumnName = "id" )
    private Set<Datacenter> datacenters;

    private Continent() {
        datacenters = new HashSet<>();
    }

    Continent( String id, String name, Set<Datacenter> datacenters ) {
        super( id, name );
        this.datacenters = datacenters;
    }

    public boolean addDatacenter( Datacenter datacenter ) {
        if( this.datacenters == null )
            this.datacenters = new HashSet<>();
        return this.datacenters.add( datacenter );
    }

    public boolean removeDatacenter( Datacenter datacenter ) {
        return this.datacenters.remove( datacenter );
    }

    @Override
    public LocationType getType() {
        return LocationType.CONTINENT;
    }

    @Override
    public Set<Server> getServers() {
        return datacenters.stream()
                .map( Datacenter::getServers )
                .flatMap( Set::stream )
                .collect( Collectors.toSet() );
    }

    public Set<Datacenter> getDatacenters() {
        return datacenters;
    }

    public static class ContinentBuilder extends LocationBuilder<ContinentBuilder> {

        private Set<Datacenter> datacenters;

        public static ContinentBuilder aContinentBuilder() {
            return new ContinentBuilder();
        }

        public ContinentBuilder withDatacenters( Set<Datacenter> datacenters ) {
            this.datacenters = datacenters;
            return this;
        }

        public Continent build() {
            return new Continent( id, name, datacenters );
        }
    }
}
