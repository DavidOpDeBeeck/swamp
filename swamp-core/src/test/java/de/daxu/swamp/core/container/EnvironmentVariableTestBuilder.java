package de.daxu.swamp.core.container;

import static de.daxu.swamp.core.container.EnvironmentVariable.Builder;

public class EnvironmentVariableTestBuilder {

    private String name = "an environment variable name";
    private String value = "an environment variable value";

    public static EnvironmentVariableTestBuilder anEnvironmentVariable() {
        return new EnvironmentVariableTestBuilder();
    }

    public EnvironmentVariableTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public EnvironmentVariableTestBuilder withValue( String value ) {
        this.value = value;
        return this;
    }

    public EnvironmentVariable build() {
        return Builder.anEnvironmentVariable()
                .withName( name )
                .withValue( value )
                .build();
    }
}