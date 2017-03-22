package de.daxu.swamp.api.continent.converter;

import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.continent.Continent;
import org.springframework.stereotype.Component;

import static de.daxu.swamp.core.continent.Continent.Builder.aContinent;

@Component
public class ContinentConverter implements DTOConverter<Continent, ContinentDTO>, DomainConverter<ContinentDTO, Continent> {

    @Override
    public ContinentDTO toDTO(Continent continent) {
        return new ContinentDTO.Builder()
                .withId(continent.getId())
                .withName(continent.getName())
                .withDatacenters(continent.getDatacenters() != null ? continent.getDatacenters().size() : 0)
                .build();
    }


    @Override
    public Continent toDomain(ContinentDTO dto) {
        return aContinent()
                .withId(dto.getId())
                .withName(dto.getName())
                .build();
    }
}
