package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.DatacenterDTO;
import de.daxu.swamp.core.location.datacenter.Datacenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.datacenter.Datacenter.DatacenterBuilder.aDatacenter;

@Component
public class DatacenterConverter implements DTOConverter<Datacenter, DatacenterDTO>, DomainConverter<DatacenterDTO, Datacenter> {

    @Autowired
    ServerConverter serverConverter;

    @Override
    public DatacenterDTO toDTO( Datacenter datacenter ) {
        DatacenterDTO dto = new DatacenterDTO();
        dto.id = datacenter.getId();
        dto.name = datacenter.getName();
        dto.servers = datacenter.getServers() != null ? datacenter.getServers().size() : 0;
        dto.type = datacenter.getType();
        return dto;
    }

    @Override
    public Datacenter toDomain( DatacenterDTO dto ) {
        return aDatacenter()
                .withId( dto.id )
                .withName( dto.name )
                .build();
    }
}
