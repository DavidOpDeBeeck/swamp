package de.daxu.swamp.core.container;

import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Location;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.configuration.ImageConfigurationTestBuilder.anImageConfigurationTestBuilder;
import static de.daxu.swamp.core.container.EnvironmentVariableTestBuilder.anEnvironmentVariableTestBuilder;
import static de.daxu.swamp.core.container.PortMappingTestBuilder.aPortMappingTestBuilder;

public class ContainerTestBuilder {

    private String name = "a container name";
    private RunConfiguration runConfiguration = anImageConfigurationTestBuilder().build();
    private Set<Location> potentialLocations = newHashSet();
    private Set<PortMapping> portMappings = newHashSet( aPortMappingTestBuilder().build() );
    private Set<EnvironmentVariable> environmentVariables = newHashSet( anEnvironmentVariableTestBuilder().build() );

    public static ContainerTestBuilder aContainerTestBuilder() {
        return new ContainerTestBuilder();
    }

    public ContainerTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ContainerTestBuilder withRunConfiguration( RunConfiguration runConfiguration ) {
        this.runConfiguration = runConfiguration;
        return this;
    }

    public ContainerTestBuilder withPotentialLocations( Set<Location> potentialLocations ) {
        this.potentialLocations = potentialLocations;
        return this;
    }

    public ContainerTestBuilder withPortMappings( Set<PortMapping> portMappings ) {
        this.portMappings = portMappings;
        return this;
    }

    public ContainerTestBuilder withEnvironmentVariables( Set<EnvironmentVariable> environmentVariables ) {
        this.environmentVariables = environmentVariables;
        return this;
    }

    public Container build() {
        return new Container( name, runConfiguration, potentialLocations, portMappings, environmentVariables );
    }
}