package de.daxu.swamp.api.container.dto;

import de.daxu.swamp.api.configuration.dto.RunConfigurationDTO;
import de.daxu.swamp.api.location.dto.LocationDTO;

import java.util.Set;

public class ContainerDTO {

    public String id;
    public String name;
    public RunConfigurationDTO runConfiguration;
    public Set<LocationDTO> potentialLocations;
    public Set<PortMappingDTO> portMappings;
    public Set<EnvironmentVariableDTO> environmentVariables;

}