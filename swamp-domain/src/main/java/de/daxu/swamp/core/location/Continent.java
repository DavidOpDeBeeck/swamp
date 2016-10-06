package de.daxu.swamp.core.location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "continent" )
@DiscriminatorValue( "continent" )
public class Continent extends Location {

    @OneToMany( orphanRemoval = true, cascade = CascadeType.REMOVE )
    @JoinTable(
            name = "continent_datacenter",
            joinColumns = { @JoinColumn( name = "continent_id", referencedColumnName = "id" ) },
            inverseJoinColumns = { @JoinColumn( name = "datacenter_id", referencedColumnName = "id" ) } )
    private List<Datacenter> datacenters;

    public Continent() {
    }

    public Continent( String id, String name, List<Datacenter> datacenters ) {
        super( id, name );
        this.datacenters = datacenters;
    }

    public boolean addDatacenter( Datacenter datacenter ) {
        if ( this.datacenters == null )
            this.datacenters = new ArrayList<>();
        return this.datacenters.add( datacenter );
    }

    public boolean removeDatacenter( Datacenter datacenter ) {
        return this.datacenters.remove( datacenter );
    }

    @Override
    public LocationType getType() {
        return LocationType.CONTINENT;
    }

    public List<Datacenter> getDatacenters() {
        return datacenters;
    }

    public static class ContinentBuilder extends LocationBuilder<ContinentBuilder> {

        private List<Datacenter> datacenters;

        public static ContinentBuilder continentBuilder() {
            return new ContinentBuilder();
        }

        public ContinentBuilder withDatacenters( List<Datacenter> datacenters ) {
            this.datacenters = datacenters;
            return this;
        }

        public Continent build() {
            return new Continent( id, name, datacenters );
        }
    }
}
