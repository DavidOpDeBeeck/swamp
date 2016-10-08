package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ContinentDTO;
import de.daxu.swamp.api.dto.location.DatacenterDTO;
import de.daxu.swamp.api.dto.location.LocationDTO;
import de.daxu.swamp.api.dto.location.ServerDTO;
import de.daxu.swamp.core.location.Continent;
import de.daxu.swamp.core.location.Datacenter;
import de.daxu.swamp.core.location.Location;
import de.daxu.swamp.core.location.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationConverter implements DTOConverter<Location, LocationDTO>, DomainConverter<LocationDTO, Location> {

    @Autowired
    ContinentConverter continentConverter;

    @Autowired
    DatacenterConverter datacenterConverter;

    @Autowired
    ServerConverter serverConverter;

    @Override
    public LocationDTO toDTO( Location location ) {
        if ( location == null || location.getType() == null ) return null;
        switch ( location.getType() ) {
            case CONTINENT:
                return continentConverter.toDTO( ( Continent ) location );
            case DATACENTER:
                return datacenterConverter.toDTO( ( Datacenter ) location );
            case SERVER:
                return serverConverter.toDTO( ( Server ) location );
            default:
                return null;
        }
    }

    @Override
    public Location toDomain( LocationDTO dto ) {
        if ( dto == null || dto.type == null ) return null;
        switch ( dto.type ) {
            case CONTINENT:
                return continentConverter.toDomain( ( ContinentDTO ) dto );
            case DATACENTER:
                return datacenterConverter.toDomain( ( DatacenterDTO ) dto );
            case SERVER:
                return serverConverter.toDomain( ( ServerDTO ) dto );
            default:
                return null;
        }
    }
}
