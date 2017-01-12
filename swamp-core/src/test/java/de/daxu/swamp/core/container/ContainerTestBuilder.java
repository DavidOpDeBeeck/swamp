package de.daxu.swamp.core.container;

import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.container.Container.Builder;
import de.daxu.swamp.core.location.Location;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.core.configuration.ImageConfigurationTestBuilder.anImageConfiguration;
import static de.daxu.swamp.core.container.EnvironmentVariableTestBuilder.anEnvironmentVariable;
import static de.daxu.swamp.core.container.PortMappingTestBuilder.aPortMapping;

public class ContainerTestBuilder {

    private Set<String> aliases = newHashSet( "a container name" );
    private RunConfiguration runConfiguration = anImageConfiguration().build();
    private Set<Location> potentialLocations = newHashSet();
    private Set<PortMapping> portMappings = newHashSet( aPortMapping().build() );
    private Set<EnvironmentVariable> environmentVariables = newHashSet( anEnvironmentVariable().build() );

    public static ContainerTestBuilder aContainer() {
        return new ContainerTestBuilder();
    }

    public ContainerTestBuilder withAliases( Set<String> aliases ) {
        this.aliases = aliases;
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
        return Builder.aContainer()
                .withAliases( aliases )
                .withRunConfiguration( runConfiguration )
                .withPotentialLocations( potentialLocations )
                .withPortMappings( portMappings )
                .withEnvironmentVariables( environmentVariables )
                .build();
    }
}