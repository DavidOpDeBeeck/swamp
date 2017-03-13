package de.daxu.swamp.api.container.dto;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.EnvironmentVariables.ENVIRONMENT_VARIABLE_NAME;
import static de.daxu.swamp.core.ProjectTestConstants.Containers.EnvironmentVariables.ENVIRONMENT_VARIABLE_VALUE;

public class EnvironmentVariableDTOTestBuilder {

    private String name = ENVIRONMENT_VARIABLE_NAME;
    private String value = ENVIRONMENT_VARIABLE_VALUE;

    public static EnvironmentVariableDTOTestBuilder anEnvironmentVariableDTO() {
        return new EnvironmentVariableDTOTestBuilder();
    }

    public EnvironmentVariableDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public EnvironmentVariableDTOTestBuilder withValue( String value ) {
        this.value = value;
        return this;
    }

    public EnvironmentVariableDTO build() {
        EnvironmentVariableDTO dto = new EnvironmentVariableDTO();
        dto.name = this.name;
        dto.value = this.value;
        return dto;
    }
}