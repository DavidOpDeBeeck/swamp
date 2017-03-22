package de.daxu.swamp.core.location;

import de.daxu.swamp.core.continent.ContinentRepository;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.datacenter.DatacenterRepository;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.core.server.ServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final ContinentRepository continentRepository;
    private final DatacenterRepository datacenterRepository;
    private final ServerRepository serverRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository,
                           ContinentRepository continentRepository,
                           DatacenterRepository datacenterRepository,
                           ServerRepository serverRepository) {
        this.locationRepository = locationRepository;
        this.continentRepository = continentRepository;
        this.datacenterRepository = datacenterRepository;
        this.serverRepository = serverRepository;
    }

    public Server addServerToDatacenter(Datacenter datacenter, Server server) {
        serverRepository.save(server);
        datacenter.addServer(server);
        datacenterRepository.save(datacenter);
        return server;
    }

    public void removeServerFromDatacenter(Datacenter datacenter, Server server) {
        serverRepository.delete(server);
        datacenter.removeServer(server);
        datacenterRepository.save(datacenter);
    }

    public Location getLocation(String id) {
        return locationRepository.findOne(id);
    }

    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    public Datacenter updateDatacenter(Datacenter datacenter) {
        return datacenterRepository.save(datacenter);
    }

    public Datacenter getDatacenter(String id) {
        return datacenterRepository.findOne(id);
    }
}
