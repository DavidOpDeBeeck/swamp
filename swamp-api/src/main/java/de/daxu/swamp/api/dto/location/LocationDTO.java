package de.daxu.swamp.api.dto.location;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.daxu.swamp.core.location.LocationType;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true )
@JsonSubTypes( {
        @JsonSubTypes.Type( value = ContinentDTO.class, name = "CONTINENT" ),
        @JsonSubTypes.Type( value = DatacenterDTO.class, name = "DATACENTER" ),
        @JsonSubTypes.Type( value = ServerDTO.class, name = "SERVER" )
} )
public abstract class LocationDTO {

    public String id;
    public String name;
    public LocationType type;

}
