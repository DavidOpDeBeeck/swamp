package de.daxu.swamp.service;

import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.Server;

import java.util.Collection;
import java.util.List;

public interface LocationService {

    Continent createContinent( Continent continent );

    void deleteContinent( Continent continent );

    Continent getContinent( String id );

    Collection<Continent> getAllContinents();

    Datacenter getDatacenter( String id );

    Datacenter addDatacenterToContinent( Continent continent, Datacenter datacenter );

    void removeDatacenterFromContinent( Continent continent, Datacenter datacenter );

    Server addServerToDatacenter( Datacenter datacenter, Server server );

    void removeServerFromDatacenter( Datacenter datacenter, Server server );

    Server getServer( String id );

    Location getLocation( String id );

    List<Location> getAllLocation();

    Continent updateContinent( Continent continent );
}
