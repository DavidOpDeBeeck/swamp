package de.daxu.swamp.api.container.dto;

public class EnvironmentVariableDTOTestBuilder {

    private String name = "an environment variable name";
    private String value = "an environment variable value";

    public static EnvironmentVariableDTOTestBuilder anEnvironmentVariableDTOTestBuilder() {
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