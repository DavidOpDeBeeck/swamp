package de.daxu.swamp.api.continent.converter;

import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.continent.Continent;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.continent.Continent.ContinentBuilder.aContinentBuilder;

@Component
public class ContinentConverter implements DTOConverter<Continent, ContinentDTO>, DomainConverter<ContinentDTO, Continent> {

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
