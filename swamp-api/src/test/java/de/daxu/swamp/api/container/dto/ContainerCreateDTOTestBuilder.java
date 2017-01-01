package de.daxu.swamp.api.container.dto;

import de.daxu.swamp.api.configuration.dto.RunConfigurationDTO;
import de.daxu.swamp.api.location.dto.LocationDTO;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.api.configuration.dto.ImageConfigurationDTOTestBuilder.anImageConfigurationDTO;
import static de.daxu.swamp.api.container.dto.EnvironmentVariableDTOTestBuilder.anEnvironmentVariableDTO;
import static de.daxu.swamp.api.container.dto.PortMappingDTOTestBuilder.aPortMappingDTO;

public class ContainerCreateDTOTestBuilder {

    private String name = "a container name";
    private RunConfigurationDTO runConfiguration = anImageConfigurationDTO().build();
    private Set<LocationDTO> potentialLocations = newHashSet();
    private Set<PortMappingDTO> portMappings = newHashSet( aPortMappingDTO().build() );
    private Set<EnvironmentVariableDTO> environmentVariables = newHashSet( anEnvironmentVariableDTO().build() );

    public static ContainerCreateDTOTestBuilder aContainerCreateDTO() {
        return new ContainerCreateDTOTestBuilder();
    }

    public ContainerCreateDTOTestBuilder withName( String name ) {
        this.name = name;
        return this;
    }

    public ContainerCreateDTOTestBuilder withRunConfiguration( RunConfigurationDTO runConfiguration ) {
        this.runConfiguration = runConfiguration;
        return this;
    }

    public ContainerCreateDTOTestBuilder withPotentialLocations( Set<LocationDTO> potentialLocations ) {
        this.potentialLocations = potentialLocations;
        return this;
    }

    public ContainerCreateDTOTestBuilder withPortMappings( Set<PortMappingDTO> portMappings ) {
        this.portMappings = portMappings;
        return this;
    }

    public ContainerCreateDTOTestBuilder withEnvironmentVariables( Set<EnvironmentVariableDTO> environmentVariables ) {
        this.environmentVariables = environmentVariables;
        return this;
    }

    public ContainerCreateDTO build() {
        ContainerCreateDTO dto = new ContainerCreateDTO();
        dto.name = this.name;
        dto.portMappings = this.portMappings;
        dto.runConfiguration = this.runConfiguration;
        dto.environmentVariables = this.environmentVariables;
        dto.potentialLocations = this.potentialLocations;
        return dto;
    }
}