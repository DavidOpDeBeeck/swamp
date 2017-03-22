package de.daxu.swamp.core.containertemplate;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "environment_variable")
public class EnvironmentVariable extends Identifiable {

    @NotNull(message = "{NotNull.EnvironmentVariable.name}")
    @Column(name = "name")
    private String name;

    @NotNull(message = "{NotNull.EnvironmentVariable.value}")
    @Column(name = "value")
    private String value;

    private EnvironmentVariable() {
    }

    private EnvironmentVariable(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String asString() {
        return String.format("%s=%s", name, value);
    }

    public static class Builder {

        private String name;
        private String value;

        public static Builder anEnvironmentVariable() {
            return new Builder();
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public EnvironmentVariable build() {
            return new EnvironmentVariable(name, value);
        }
    }
}
