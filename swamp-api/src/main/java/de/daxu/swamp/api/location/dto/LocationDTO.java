package de.daxu.swamp.api.location.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.daxu.swamp.api.continent.dto.ContinentDTO;
import de.daxu.swamp.api.datacenter.dto.DatacenterDTO;
import de.daxu.swamp.api.server.dto.ServerDTO;
import de.daxu.swamp.common.ValueObject;
import de.daxu.swamp.core.location.LocationType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContinentDTO.class, name = "CONTINENT"),
        @JsonSubTypes.Type(value = DatacenterDTO.class, name = "DATACENTER"),
        @JsonSubTypes.Type(value = ServerDTO.class, name = "SERVER")
})
public abstract class LocationDTO extends ValueObject {

    private String id;
    private String name;
    private LocationType type;

    protected LocationDTO() {
    }

    protected LocationDTO(String id, String name, LocationType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocationType getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    public static abstract class Builder<T extends Builder> {

        protected String id;
        protected String name;

        public T withId(String id) {
            this.id = id;
            return (T) this;
        }

        public T withName(String name) {
            this.name = name;
            return (T) this;
        }

        public abstract LocationDTO build();
    }
}
