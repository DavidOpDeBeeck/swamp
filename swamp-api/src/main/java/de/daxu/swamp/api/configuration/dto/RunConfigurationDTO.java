package de.daxu.swamp.api.configuration.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.daxu.swamp.common.ValueObject;
import de.daxu.swamp.core.configuration.RunConfigurationType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageConfigurationDTO.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = GitConfigurationDTO.class, name = "GIT"),
        @JsonSubTypes.Type(value = DockerfileConfigurationDTO.class, name = "DOCKERFILE")
})
public abstract class RunConfigurationDTO extends ValueObject {

    private final RunConfigurationType type;

    RunConfigurationDTO(RunConfigurationType type) {
        this.type = type;
    }

    public RunConfigurationType getType() {
        return type;
    }

}
