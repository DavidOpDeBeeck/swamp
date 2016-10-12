package de.daxu.swamp.core.location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table( name = "datacenter" )
@DiscriminatorValue( "datacenter" )
public class Datacenter extends Location {

    @OneToMany( orphanRemoval = true, cascade = CascadeType.REMOVE )
    @JoinTable(
            name = "datacenter_server",
            joinColumns = { @JoinColumn( name = "datacenter_id", referencedColumnName = "id" ) },
            inverseJoinColumns = { @JoinColumn( name = "server_id", referencedColumnName = "id" ) } )
    private List<Server> servers;

    private Datacenter() {
    }

    Datacenter( String id, String name, List<Server> servers ) {
        super( id, name );
        this.servers = servers;
    }

    public boolean addServer( Server server ) {
        if ( this.servers == null )
            this.servers = new ArrayList<>();
        return this.servers.add( server );
    }

    public boolean removeServer( Server server ) {
        return this.servers.remove( server );
    }

    public List<Server> getServers() {
        return servers;
    }

    @Override
    public LocationType getType() {
        return LocationType.DATACENTER;
    }

    public static class DatacenterBuilder extends LocationBuilder<DatacenterBuilder> {

        private List<Server> servers;

        public static DatacenterBuilder aDatacenter() {
            return new DatacenterBuilder();
        }

        public DatacenterBuilder withServers( List<Server> servers ) {
            this.servers = servers;
            return this;
        }

        public Datacenter build() {
            return new Datacenter( id, name, servers );
        }
    }
}
