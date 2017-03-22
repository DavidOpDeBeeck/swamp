package de.daxu.swamp.api.datacenter.converter;

import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.datacenter.Datacenter;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.datacenter.Datacenter.Builder.aDatacenter;

@Component
public class DatacenterConverter implements DTOConverter<Datacenter, DatacenterDTO>, DomainConverter<DatacenterDTO, Datacenter> {

    @Override
    public DatacenterDTO toDTO(Datacenter datacenter) {
        return new DatacenterDTO.Builder()
                .withId(datacenter.getId())
                .withName(datacenter.getName())
                .withDatacenters(datacenter.getServers() != null ? datacenter.getServers().size() : 0)
                .build();
    }

    @Override
    public Datacenter toDomain(DatacenterDTO dto) {
        return aDatacenter()
                .withId(dto.getId())
                .withName(dto.getName())
                .build();
    }
}
