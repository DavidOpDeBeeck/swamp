package de.daxu.swamp.api.containertemplate.converter;

import de.daxu.swamp.api.configuration.converter.RunConfigurationConverter;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateCreateDTO;
import de.daxu.swamp.api.location.converter.LocationConverter;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static de.daxu.swamp.core.containertemplate.ContainerTemplate.Builder.aContainerTemplate;

@Component
public class ContainerTemplateCreateConverter implements DomainConverter<ContainerTemplateCreateDTO, ContainerTemplate> {

    private final LocationConverter locationConverter;
    private final RunConfigurationConverter configurationConverter;
    private final PortMappingConverter portMappingConverter;
    private final EnvironmentVariableConverter environmentVariableConverter;

    @Autowired
    public ContainerTemplateCreateConverter(LocationConverter locationConverter,
                                            RunConfigurationConverter configurationConverter,
                                            PortMappingConverter portMappingConverter,
                                            EnvironmentVariableConverter environmentVariableConverter) {
        this.locationConverter = locationConverter;
        this.configurationConverter = configurationConverter;
        this.portMappingConverter = portMappingConverter;
        this.environmentVariableConverter = environmentVariableConverter;
    }

    @Override
    public ContainerTemplate toDomain(ContainerTemplateCreateDTO dto) {
        return aContainerTemplate()
                .withAliases(dto.getAliases())
                .withRunConfiguration(configurationConverter.toDomain(dto.getRunConfiguration()))
                .withPotentialLocations(dto.getPotentialLocations().stream()
                        .map(locationConverter::toDomain)
                        .collect(Collectors.toSet()))
                .withPortMappings(dto.getPortMappings().stream()
                        .map(portMappingConverter::toDomain)
                        .collect(Collectors.toSet()))
                .withEnvironmentVariables(dto.getEnvironmentVariables().stream()
                        .map(environmentVariableConverter::toDomain)
                        .collect(Collectors.toSet()))
                .build();
    }
}
