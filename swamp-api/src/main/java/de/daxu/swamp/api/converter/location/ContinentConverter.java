package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ContinentDTO;
import de.daxu.swamp.core.location.continent.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.continent.Continent.ContinentBuilder.aContinentBuilder;

@Component
public class ContinentConverter implements DTOConverter<Continent, ContinentDTO>, DomainConverter<ContinentDTO, Continent> {

    @Autowired
    DatacenterConverter datacenterConverter;

    @Override
    public ContinentDTO toDTO( Continent continent ) {
        ContinentDTO dto = new ContinentDTO();
        dto.id = continent.getId();
        dto.name = continent.getName();
        dto.datacenters = continent.getDatacenters() != null ? continent.getDatacenters().size() : 0;
        dto.type = continent.getType();
        return dto;
    }


    @Override
    public Continent toDomain( ContinentDTO dto ) {
        return aContinentBuilder()
                .withId( dto.id )
                .withName( dto.name )
                .build();
    }
}
