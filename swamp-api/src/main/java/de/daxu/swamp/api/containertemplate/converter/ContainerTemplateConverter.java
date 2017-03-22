package de.daxu.swamp.api.containertemplate.converter;

import de.daxu.swamp.api.configuration.converter.RunConfigurationConverter;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateDTO;
import de.daxu.swamp.api.location.converter.LocationConverter;
import de.daxu.swamp.common.dto.DTOConverter;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ContainerTemplateConverter implements DTOConverter<ContainerTemplate, ContainerTemplateDTO> {

    private final RunConfigurationConverter configurationConverter;
    private final LocationConverter locationConverter;
    private final PortMappingConverter portMappingConverter;
    private final EnvironmentVariableConverter environmentVariableConverter;

    @Autowired
    public ContainerTemplateConverter(RunConfigurationConverter configurationConverter, LocationConverter locationConverter, PortMappingConverter portMappingConverter, EnvironmentVariableConverter environmentVariableConverter) {
        this.configurationConverter = configurationConverter;
        this.locationConverter = locationConverter;
        this.portMappingConverter = portMappingConverter;
        this.environmentVariableConverter = environmentVariableConverter;
    }

    @Override
    public ContainerTemplateDTO toDTO(ContainerTemplate containerTemplate) {
        return new ContainerTemplateDTO.Builder()
                .withId(containerTemplate.getId())
                .withAliases(containerTemplate.getAliases())
                .withRunConfiguration(configurationConverter.toDTO(containerTemplate.getRunConfiguration()))
                .withPortMappings(containerTemplate.getPortMappings()
                        .stream()
                        .map(portMappingConverter::toDTO)
                        .collect(Collectors.toSet()))
                .withPotentialLocations(containerTemplate.getPotentialLocations()
                        .stream()
                        .map(locationConverter::toDTO)
                        .collect(Collectors.toSet()))
                .withEnvironmentVariables(containerTemplate.getEnvironmentVariables()
                        .stream()
                        .map(environmentVariableConverter::toDTO)
                        .collect(Collectors.toSet()))
                .build();
    }
}
