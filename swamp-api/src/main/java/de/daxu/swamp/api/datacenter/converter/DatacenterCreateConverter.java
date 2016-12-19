package de.daxu.swamp.api.datacenter.converter;

import de.daxu.swamp.api.datacenter.dto.DatacenterCreateDTO;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.datacenter.Datacenter;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.datacenter.Datacenter.DatacenterBuilder.aDatacenterBuilder;

@Component
public class DatacenterCreateConverter implements DomainConverter<DatacenterCreateDTO, Datacenter> {

    @Override
    public Datacenter toDomain( DatacenterCreateDTO dto ) {
        return aDatacenterBuilder()
                .withName( dto.name )
                .build();
    }
}
