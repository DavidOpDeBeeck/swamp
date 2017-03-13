package de.daxu.swamp.api.datacenter.dto;

import static de.daxu.swamp.core.LocationTestConstants.Datacenters.ANOTHER_DATACENTER_NAME;
import static de.daxu.swamp.core.LocationTestConstants.Datacenters.DATACENTER_NAME;

public class DatacenterCreateDTOTestBuilder {

    private String name = DATACENTER_NAME;

    public static DatacenterCreateDTOTestBuilder aDatacenterCreateDTO() {
        return new DatacenterCreateDTOTestBuilder();
    }

    public static DatacenterCreateDTOTestBuilder anotherDatacenterCreateDTO() {
        return new DatacenterCreateDTOTestBuilder()
                .withName(ANOTHER_DATACENTER_NAME);
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