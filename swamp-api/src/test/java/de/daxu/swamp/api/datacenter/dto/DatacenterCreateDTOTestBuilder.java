package de.daxu.swamp.api.datacenter.dto;

public class DatacenterCreateDTOTestBuilder {

    private String name = "a datacenter name";

    public static DatacenterCreateDTOTestBuilder aDatacenterCreateDTOTestBuilder() {
        return new DatacenterCreateDTOTestBuilder();
    }

    public DatacenterCreateDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public DatacenterCreateDTO build() {
        DatacenterCreateDTO dto = new DatacenterCreateDTO();
        dto.name = name;
        return dto;
    }
}