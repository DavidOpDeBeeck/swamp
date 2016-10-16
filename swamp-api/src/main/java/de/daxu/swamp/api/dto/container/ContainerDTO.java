package de.daxu.swamp.api.dto.container;

import de.daxu.swamp.api.dto.container.configuration.PortMappingDTO;
import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.api.dto.location.LocationDTO;

import java.util.Collection;
import java.util.List;

public class ContainerDTO {

    public String id;
    public String arguments;
    public RunConfigurationDTO runConfiguration;
    public Collection<LocationDTO> potentialLocations;
    public List<PortMappingDTO> portMappings;

}
