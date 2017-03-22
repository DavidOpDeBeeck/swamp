package de.daxu.swamp.api.location.converter;

import de.daxu.swamp.api.continent.converter.ContinentConverter;
import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.api.datacenter.converter.DatacenterConverter;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.api.location.dto.LocationDTO;
import de.daxu.swamp.api.server.converter.ServerConverter;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.continent.Continent;
import de.daxu.swamp.core.datacenter.Datacenter;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter implements DTOConverter<Location, LocationDTO>, DomainConverter<LocationDTO, Location> {

    private final ContinentConverter continentConverter;
    private final DatacenterConverter datacenterConverter;
    private final ServerConverter serverConverter;

    @Autowired
    public LocationConverter(ContinentConverter continentConverter,
                             DatacenterConverter datacenterConverter,
                             ServerConverter serverConverter) {
        this.continentConverter = continentConverter;
        this.datacenterConverter = datacenterConverter;
        this.serverConverter = serverConverter;
    }

    @Override
    public LocationDTO toDTO(Location location) {
        if (location == null || location.getType() == null) return null;
        switch (location.getType()) {
            case CONTINENT:
                return continentConverter.toDTO((Continent) location);
            case DATACENTER:
                return datacenterConverter.toDTO((Datacenter) location);
            case SERVER:
                return serverConverter.toDTO((Server) location);
            default:
                return null;
        }
    }

    @Override
    public Location toDomain(LocationDTO dto) {
        if (dto == null || dto.getType() == null) return null;
        switch (dto.getType()) {
            case CONTINENT:
                return continentConverter.toDomain((ContinentDTO) dto);
            case DATACENTER:
                return datacenterConverter.toDomain((DatacenterDTO) dto);
            case SERVER:
                return serverConverter.toDomain((ServerDTO) dto);
            default:
                return null;
        }
    }
}
