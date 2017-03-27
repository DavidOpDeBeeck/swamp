package de.daxu.swamp.api.credentials.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.daxu.swamp.common.ValueObject;
import de.daxu.swamp.core.credentials.CredentialsType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = UsernamePasswordCredentialsDTO.class, name = "USERNAME_PASSWORD")
})
public abstract class CredentialsDTO extends ValueObject {

    private final CredentialsType type;

    CredentialsDTO(CredentialsType type) {
        this.type = type;
    }

    public CredentialsType getType() {
        return type;
    }

}