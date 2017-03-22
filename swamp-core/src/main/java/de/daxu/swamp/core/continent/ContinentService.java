package de.daxu.swamp.core.continent;

import de.daxu.swamp.core.datacenter.Datacenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContinentService {

    private final ContinentRepository continentRepository;

    @Autowired
    public ContinentService(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    public Continent createContinent(Continent continent) {
        return continentRepository.save(continent);
    }

    public Continent updateContinent(Continent continent) {
        return continentRepository.save(continent);
    }

    public void deleteContinent(Continent continent) {
        continentRepository.delete(continent);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addDatacenterToContinent(String continentId, Datacenter datacenter) {
        Continent continent = getContinent(continentId);
        continent.addDatacenter(datacenter);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeDatacenterFromContinent(String continentId, Datacenter datacenter) {
        Continent continent = getContinent(continentId);
        continent.removeDatacenter(datacenter);
    }

    public Continent getContinent(String id) {
        return continentRepository.findOne(id);
    }

    public List<Continent> getAllContinents() {
        return continentRepository.findAll();
    }
}
