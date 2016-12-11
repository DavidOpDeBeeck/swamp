package de.daxu.swamp.api.dto.container.configuration;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.daxu.swamp.core.runconfiguration.RunConfigurationType;

@JsonTypeInfo( use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true )
@JsonSubTypes( {
        @JsonSubTypes.Type( value = ImageConfigurationDTO.class, name = "IMAGE" ),
        @JsonSubTypes.Type( value = GitConfigurationDTO.class, name = "GIT" ),
        @JsonSubTypes.Type( value = DockerfileConfigurationDTO.class, name = "DOCKERFILE" )
} )
public abstract class RunConfigurationDTO {

    public RunConfigurationType type;
}
