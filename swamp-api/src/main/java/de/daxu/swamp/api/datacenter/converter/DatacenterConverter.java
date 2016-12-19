package de.daxu.swamp.api.datacenter.converter;

import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.datacenter.Datacenter;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.datacenter.Datacenter.DatacenterBuilder.aDatacenterBuilder;

@Component
public class DatacenterConverter implements DTOConverter<Datacenter, DatacenterDTO>, DomainConverter<DatacenterDTO, Datacenter> {

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
        return aDatacenterBuilder()
                .withId( dto.id )
                .withName( dto.name )
                .build();
    }
}
