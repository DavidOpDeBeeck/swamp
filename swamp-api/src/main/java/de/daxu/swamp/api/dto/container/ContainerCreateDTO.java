package de.daxu.swamp.api.dto.container;

import de.daxu.swamp.api.dto.container.configuration.PortMappingDTO;
import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.api.dto.location.LocationDTO;

import java.util.Set;

public class ContainerCreateDTO {

    public String name;
    public RunConfigurationDTO runConfiguration;
    public Set<LocationDTO> potentialLocations;
    public Set<PortMappingDTO> portMappings;
    public Set<EnvironmentVariableDTO> environmentVariables;

}
