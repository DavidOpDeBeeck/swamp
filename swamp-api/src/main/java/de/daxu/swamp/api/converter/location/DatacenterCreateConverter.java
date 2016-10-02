package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.DatacenterCreateDTO;
import de.daxu.swamp.core.location.Datacenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.Datacenter.DatacenterBuilder.datacenterBuilder;

@Component
public class DatacenterCreateConverter implements DomainConverter<DatacenterCreateDTO, Datacenter> {

    @Autowired
    ServerConverter serverConverter;

    @Override
    public Datacenter toDomain( DatacenterCreateDTO dto ) {
        return datacenterBuilder()
                .withName( dto.name )
                .build();
    }
}
