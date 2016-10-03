package de.daxu.swamp.service;

import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.repository.location.ContinentRepository;
import de.daxu.swamp.repository.location.DatacenterRepository;
import de.daxu.swamp.repository.location.LocationRepository;
import de.daxu.swamp.repository.location.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ContinentRepository continentRepository;

    @Autowired
    DatacenterRepository datacenterRepository;

    @Autowired
    ServerRepository serverRepository;

    @Override
    public Continent createContinent( Continent continent ) {
        return continentRepository.save( continent );
    }

    @Override
    public void deleteContinent( Continent continent ) {
        continentRepository.delete( continent );
    }

    @Override
    public Datacenter addDatacenterToContinent( Continent continent, Datacenter datacenter ) {
        datacenterRepository.save( datacenter );
        continent.addDatacenter( datacenter );
        continentRepository.save( continent );
        return datacenter;
    }

    @Override
    public void removeDatacenterFromContinent( Continent continent, Datacenter datacenter ) {
        datacenterRepository.delete( datacenter );
        continent.removeDatacenter( datacenter );
        continentRepository.save( continent );
    }

    @Override
    public Server addServerToDatacenter( Datacenter datacenter, Server server ) {
        serverRepository.save( server );
        datacenter.addServer( server );
        datacenterRepository.save( datacenter );
        return server;
    }

    @Override
    public void removeServerFromDatacenter( Datacenter datacenter, Server server ) {
        serverRepository.delete( server );
        datacenter.removeServer( server );
        datacenterRepository.save( datacenter );
    }

    @Override
    public Location getLocation( String id ) {
        return locationRepository.findOne( id );
    }

    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public Continent updateContinent( Continent continent ) {
        return continentRepository.save( continent );
    }

    @Override
    public Datacenter updateDatacenter( Datacenter datacenter ) {
        return datacenterRepository.save( datacenter );
    }

    @Override
    public Server updateServer( Server server ) {
        return serverRepository.save( server );
    }

    @Override
    public Continent getContinent( String id ) {
        return continentRepository.findOne( id );
    }

    @Override
    public Datacenter getDatacenter( String id ) {
        return datacenterRepository.findOne( id );
    }

    @Override
    public Server getServer( String id ) {
        return serverRepository.findOne( id );
    }

    @Override
    public Collection<Continent> getAllContinents() {
        return continentRepository.findAll();
    }
}
