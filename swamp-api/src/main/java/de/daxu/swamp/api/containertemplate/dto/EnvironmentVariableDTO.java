package de.daxu.swamp.api.containertemplate.dto;

import de.daxu.swamp.common.ValueObject;

public class EnvironmentVariableDTO extends ValueObject {

    private String name;
    private String value;

    @SuppressWarnings("unused")
    private EnvironmentVariableDTO() {
    }

    private EnvironmentVariableDTO(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public static class Builder {

        private String name;
        private String value;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public EnvironmentVariableDTO build() {
            return new EnvironmentVariableDTO(name, value);
        }
    }
}
