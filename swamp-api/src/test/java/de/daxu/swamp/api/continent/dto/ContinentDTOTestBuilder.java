package de.daxu.swamp.api.continent.dto;

public class ContinentDTOTestBuilder {

    private String id;
    private String name = "a continent name";
    private int datacenters = 0;

    public static ContinentDTOTestBuilder aContinentDTOTestBuilder() {
        return new ContinentDTOTestBuilder();
    }

    public ContinentDTOTestBuilder withId( String id ) {
        this.id = id;
        return this;
    }

    public ContinentDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ContinentDTOTestBuilder withDatacenters( int datacenters ) {
        this.datacenters = datacenters;
        return this;
    }

    public ContinentDTO build() {
        ContinentDTO dto = new ContinentDTO();
        dto.id = this.id;
        dto.name = this.name;
        dto.datacenters = this.datacenters;
        return dto;
    }
}