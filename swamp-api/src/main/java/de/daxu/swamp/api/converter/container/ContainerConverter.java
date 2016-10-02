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

    @Override
    public ContainerDTO toDTO( Container container ) {
        ContainerDTO dto = new ContainerDTO();
        dto.id = container.getId();
        dto.runConfiguration = configurationConverter.toDTO( container.getRunConfiguration() );
        dto.potentialLocations = container.getPotentialLocations().stream().map( locationConverter::toDTO ).collect( Collectors.toList() );
        return dto;
    }
}
