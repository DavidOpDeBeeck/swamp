package de.daxu.swamp.core.container;

import static de.daxu.swamp.core.ProjectTestConstants.Containers.EnvironmentVariables.*;
import static de.daxu.swamp.core.container.EnvironmentVariable.Builder;

public class EnvironmentVariableTestBuilder {

    private String name = ENVIRONMENT_VARIABLE_NAME;
    private String value = ENVIRONMENT_VARIABLE_VALUE;

    public static EnvironmentVariableTestBuilder anEnvironmentVariable() {
        return new EnvironmentVariableTestBuilder();
    }

    public static EnvironmentVariableTestBuilder anotherEnvironmentVariable() {
        return new EnvironmentVariableTestBuilder()
                .withName(ANOTHER_ENVIRONMENT_VARIABLE_NAME)
                .withValue(ANOTHER_ENVIRONMENT_VARIABLE_VALUE);
    }

    public EnvironmentVariableTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public EnvironmentVariableTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public EnvironmentVariable build() {
        return Builder.anEnvironmentVariable()
                .withName(name)
                .withValue(value)
                .build();
    }
}