package de.daxu.swamp.core.datacenter;

import de.daxu.swamp.core.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatacenterService {

    private final DatacenterRepository datacenterRepository;

    @Autowired
    public DatacenterService(DatacenterRepository datacenterRepository) {
        this.datacenterRepository = datacenterRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addServerToDatacenter(String datacenterId, Server server) {
        Datacenter datacenter = getDatacenter(datacenterId);
        datacenter.addServer(server);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeServerFromDatacenter(String datacenterId, Server server) {
        Datacenter datacenter = getDatacenter(datacenterId);
        datacenter.removeServer(server);
    }

    public Datacenter updateDatacenter(Datacenter datacenter) {
        return datacenterRepository.save(datacenter);
    }

    public Datacenter getDatacenter(String id) {
        return datacenterRepository.findOne(id);
    }

}
