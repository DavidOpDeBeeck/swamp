package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.LocationType;
import de.daxu.swamp.core.server.Server;

import javax.persistence.*;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

@Entity
@Table( name = "datacenter" )
@DiscriminatorValue( "datacenter" )
@SuppressWarnings( "unused" )
public class Datacenter extends Location {

    @OneToMany( fetch = FetchType.EAGER, orphanRemoval = true )
    @JoinColumn( name = "datacenter_id", referencedColumnName = "id" )
    private Set<Server> servers;

    private Datacenter() {
    }

    private Datacenter( String id, String name, Set<Server> servers ) {
        super( id, name );
        this.servers = servers;
    }

    public boolean addServer( Server server ) {
        if ( this.servers == null ) this.servers = newHashSet();
        return this.servers.add( server );
    }

    public boolean removeServer( Server server ) {
        return this.servers.remove( server );
    }

    @Override
    public Set<Server> getServers() {
        return newHashSet( servers );
    }

    @Override
    public LocationType getType() {
        return LocationType.DATACENTER;
    }

    public static class Builder extends Location.Builder<Builder> {

        private Set<Server> servers;

        public static Builder aDatacenter() {
            return new Builder();
        }

        public Builder withServers( Set<Server> servers ) {
            this.servers = servers;
            return this;
        }

        public Datacenter build() {
            return new Datacenter( id, name, servers );
        }
    }
}
