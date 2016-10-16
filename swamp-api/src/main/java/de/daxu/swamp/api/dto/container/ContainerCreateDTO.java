package de.daxu.swamp.api.dto.container;

import de.daxu.swamp.api.dto.container.configuration.PortMappingDTO;
import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.api.dto.location.LocationDTO;

import java.util.List;

public class ContainerCreateDTO {

    public String arguments;
    public RunConfigurationDTO runConfiguration;
    public List<LocationDTO> potentialLocations;
    public List<PortMappingDTO> portMappings;

}
