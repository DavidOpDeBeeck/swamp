package de.daxu.swamp.core.location;

import de.daxu.swamp.core.location.continent.Continent;
import de.daxu.swamp.core.location.continent.ContinentRepository;
import de.daxu.swamp.core.location.datacenter.Datacenter;
import de.daxu.swamp.core.location.datacenter.DatacenterRepository;
import de.daxu.swamp.core.location.server.Server;
import de.daxu.swamp.core.location.server.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final ContinentRepository continentRepository;
    private final DatacenterRepository datacenterRepository;
    private final ServerRepository serverRepository;

    @Autowired
    public LocationService( LocationRepository locationRepository,
                            ContinentRepository continentRepository,
                            DatacenterRepository datacenterRepository,
                            ServerRepository serverRepository ) {
        this.locationRepository = locationRepository;
        this.continentRepository = continentRepository;
        this.datacenterRepository = datacenterRepository;
        this.serverRepository = serverRepository;
    }

    public Continent createContinent( Continent continent ) {
        return continentRepository.save( continent );
    }

    public void deleteContinent( Continent continent ) {
        continentRepository.delete( continent );
    }

    public Datacenter addDatacenterToContinent( Continent continent, Datacenter datacenter ) {
        datacenterRepository.save( datacenter );
        continent.addDatacenter( datacenter );
        continentRepository.save( continent );
        return datacenterRepository.findByName( datacenter.getName() );
    }

    public void removeDatacenterFromContinent( Continent continent, Datacenter datacenter ) {
        datacenterRepository.delete( datacenter );
        continent.removeDatacenter( datacenter );
        continentRepository.save( continent );
    }

    public Server addServerToDatacenter( Datacenter datacenter, Server server ) {
        serverRepository.save( server );
        datacenter.addServer( server );
        datacenterRepository.save( datacenter );
        return server;
    }

    public void removeServerFromDatacenter( Datacenter datacenter, Server server ) {
        serverRepository.delete( server );
        datacenter.removeServer( server );
        datacenterRepository.save( datacenter );
    }

    public Location getLocation( String id ) {
        return locationRepository.findOne( id );
    }

    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Continent updateContinent( Continent continent ) {
        return continentRepository.save( continent );
    }

    public Datacenter updateDatacenter( Datacenter datacenter ) {
        return datacenterRepository.save( datacenter );
    }

    public Server updateServer( Server server ) {
        return serverRepository.save( server );
    }

    public Continent getContinent( String id ) {
        return continentRepository.findOne( id );
    }

    public Datacenter getDatacenter( String id ) {
        return datacenterRepository.findOne( id );
    }

    public Server getServer( String id ) {
        return serverRepository.findOne( id );
    }

    public Collection<Continent> getAllContinents() {
        return continentRepository.findAll();
    }
}
