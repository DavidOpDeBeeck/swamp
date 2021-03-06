package de.daxu.swamp.api.continent.converter;

import de.daxu.swamp.api.continent.dto.ContinentCreateDTO;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.continent.Continent;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.continent.Continent.Builder.aContinent;

@Component
public class ContinentCreateConverter implements DomainConverter<ContinentCreateDTO, Continent> {

    @Override
    public Continent toDomain(ContinentCreateDTO dto) {
        return aContinent()
                .withName(dto.getName())
                .build();
    }
}
