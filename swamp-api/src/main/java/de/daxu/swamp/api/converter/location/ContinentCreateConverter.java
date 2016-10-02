package de.daxu.swamp.api.converter.location;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.location.ContinentCreateDTO;
import de.daxu.swamp.core.location.Continent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.location.Continent.ContinentBuilder.continentBuilder;

@Component
public class ContinentCreateConverter implements DomainConverter<ContinentCreateDTO, Continent> {

    @Autowired
    DatacenterConverter datacenterConverter;

    @Override
    public Continent toDomain( ContinentCreateDTO dto ) {
        return continentBuilder()
                .withName( dto.name )
                .build();
    }
}
