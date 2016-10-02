package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.converter.container.configuration.RunConfigurationConverter;
import de.daxu.swamp.api.converter.location.LocationConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.core.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static de.daxu.swamp.core.container.Container.ContainerBuilder.containerBuilder;

@Component
public class ContainerCreateConverter implements DomainConverter<ContainerCreateDTO, Container> {

    @Autowired
    LocationConverter locationConverter;

    @Autowired
    RunConfigurationConverter configurationConverter;

    @Override
    public Container toDomain( ContainerCreateDTO dto ) {
        return containerBuilder()
                .withRunConfiguration( configurationConverter.toDomain( dto.runConfiguration ) )
                .withPotentialLocations( dto.potentialLocations.stream()
                        .map( locationConverter::toDomain )
                        .collect( Collectors.toList() ) )
                .build();
    }
}
