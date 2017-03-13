package de.daxu.swamp.api.continent.dto;

import static de.daxu.swamp.core.LocationTestConstants.Continents.ANOTHER_CONTINENT_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Continents.CONTINENT_NAME;

public class ContinentCreateDTOTestBuilder {

    private String name = CONTINENT_NAME;

    public static ContinentCreateDTOTestBuilder aContinentCreateDTO() {
        return new ContinentCreateDTOTestBuilder();
    }

    public static ContinentCreateDTOTestBuilder anotherContinentCreateDTO() {
        return new ContinentCreateDTOTestBuilder()
                .withName(ANOTHER_CONTINENT_NAME);
    }

    public ContinentCreateDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ContinentCreateDTO build() {
        ContinentCreateDTO dto = new ContinentCreateDTO();
        dto.name = name;
        return dto;
    }
}