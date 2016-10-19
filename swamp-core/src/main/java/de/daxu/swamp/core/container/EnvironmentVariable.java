package de.daxu.swamp.core.container;

import de.daxu.swamp.common.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "environment_variable" )
public class EnvironmentVariable extends Identifiable {

    @NotNull( message = "{NotNull.EnvironmentVariable.name}" )
    @Column( name = "name" )
    private String name;

    @NotNull( message = "{NotNull.EnvironmentVariable.value}" )
    @Column( name = "value" )
    private String value;

    private EnvironmentVariable() {
    }

    private EnvironmentVariable( String name, String value ) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return String.format( "%s=%s", name, value );
    }

    public static class EnvironmentVariableBuilder {

        private String name;
        private String value;

        public static EnvironmentVariableBuilder anEnvironmentVariable() {
            return new EnvironmentVariableBuilder();
        }

        public EnvironmentVariableBuilder withName( String name ) {
            this.name = name;
            return this;
        }

        public EnvironmentVariableBuilder withValue( String value ) {
            this.value = value;
            return this;
        }

        public EnvironmentVariable build() {
            return new EnvironmentVariable( name, value );
        }
    }
}
