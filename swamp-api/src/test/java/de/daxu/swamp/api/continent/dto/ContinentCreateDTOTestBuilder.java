package de.daxu.swamp.api.continent.dto;

public class ContinentCreateDTOTestBuilder {

    private String name ="a continent name";

    public static ContinentCreateDTOTestBuilder aContinentCreateDTO() {
        return new ContinentCreateDTOTestBuilder();
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