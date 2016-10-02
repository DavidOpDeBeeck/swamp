package de.daxu.swamp.api.dto.container;

import de.daxu.swamp.api.dto.container.configuration.RunConfigurationDTO;
import de.daxu.swamp.api.dto.location.LocationDTO;

import java.util.Collection;

public class ContainerDTO {

    public String id;
    public RunConfigurationDTO runConfiguration;
    public Collection<LocationDTO> potentialLocations;

}
