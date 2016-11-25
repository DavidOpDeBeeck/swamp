package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DTOConverter;
import de.daxu.swamp.api.converter.container.configuration.RunConfigurationConverter;
import de.daxu.swamp.api.converter.location.LocationConverter;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.core.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ContainerConverter implements DTOConverter<Container, ContainerDTO> {

    @Autowired
    RunConfigurationConverter configurationConverter;

    @Autowired
    LocationConverter locationConverter;

    @Autowired
    PortMappingConverter portMappingConverter;

    @Autowired
    EnvironmentVariableConverter environmentVariableConverter;

    @Override
    public ContainerDTO toDTO( Container container ) {
        ContainerDTO dto = new ContainerDTO();
        dto.id = container.getId();
        dto.name = container.getName();
        dto.runConfiguration = configurationConverter.toDTO( container.getRunConfiguration() );
        dto.potentialLocations = container.getPotentialLocations().stream().map( locationConverter::toDTO ).collect( Collectors.toSet() );
        dto.portMappings = container.getPortMappings().stream().map( portMappingConverter::toDTO ).collect( Collectors.toSet() );
        dto.environmentVariables = container.getEnvironmentVariables().stream().map( environmentVariableConverter::toDTO ).collect( Collectors.toSet() );
        return dto;
    }
}
