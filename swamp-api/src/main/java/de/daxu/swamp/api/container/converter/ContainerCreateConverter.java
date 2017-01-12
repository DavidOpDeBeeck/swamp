package de.daxu.swamp.api.container.converter;

import de.daxu.swamp.api.configuration.converter.RunConfigurationConverter;
import de.daxu.swamp.api.container.dto.ContainerCreateDTO;
import de.daxu.swamp.api.location.converter.LocationConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.container.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static de.daxu.swamp.core.container.Container.Builder.aContainer;

@Component
public class ContainerCreateConverter implements DomainConverter<ContainerCreateDTO, Container> {

    private final LocationConverter locationConverter;
    private final RunConfigurationConverter configurationConverter;
    private final PortMappingConverter portMappingConverter;
    private final EnvironmentVariableConverter environmentVariableConverter;

    @Autowired
    public ContainerCreateConverter( LocationConverter locationConverter,
                                     RunConfigurationConverter configurationConverter,
                                     PortMappingConverter portMappingConverter,
                                     EnvironmentVariableConverter environmentVariableConverter ) {
        this.locationConverter = locationConverter;
        this.configurationConverter = configurationConverter;
        this.portMappingConverter = portMappingConverter;
        this.environmentVariableConverter = environmentVariableConverter;
    }

    @Override
    public Container toDomain( ContainerCreateDTO dto ) {
        return aContainer()
                .withAliases( dto.aliases )
                .withRunConfiguration( configurationConverter.toDomain( dto.runConfiguration ) )
                .withPotentialLocations( dto.potentialLocations.stream()
                        .map( locationConverter::toDomain )
                        .collect( Collectors.toSet() ) )
                .withPortMappings( dto.portMappings.stream()
                        .map( portMappingConverter::toDomain )
                        .collect( Collectors.toSet() ) )
                .withEnvironmentVariables( dto.environmentVariables.stream()
                        .map( environmentVariableConverter::toDomain )
                        .collect( Collectors.toSet() ) )
                .build();
    }
}
