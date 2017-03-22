package de.daxu.swamp.api.containertemplate.dto;

import de.daxu.swamp.api.configuration.dto.RunConfigurationDTO;
import de.daxu.swamp.api.location.dto.LocationDTO;
import de.daxu.swamp.common.ValueObject;

import javax.validation.Valid;
import java.util.Set;

@SuppressWarnings("unused")
public class ContainerTemplateCreateDTO extends ValueObject {

    @Valid
    private RunConfigurationDTO runConfiguration;
    private Set<String> aliases;
    private Set<LocationDTO> potentialLocations;
    private Set<PortMappingDTO> portMappings;
    private Set<EnvironmentVariableDTO> environmentVariables;

    @SuppressWarnings("unused")
    private ContainerTemplateCreateDTO() {
    }

    private ContainerTemplateCreateDTO(RunConfigurationDTO runConfiguration,
                                       Set<String> aliases,
                                       Set<LocationDTO> potentialLocations,
                                       Set<PortMappingDTO> portMappings,
                                       Set<EnvironmentVariableDTO> environmentVariables) {
        this.runConfiguration = runConfiguration;
        this.aliases = aliases;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public RunConfigurationDTO getRunConfiguration() {
        return runConfiguration;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public Set<LocationDTO> getPotentialLocations() {
        return potentialLocations;
    }

    public Set<PortMappingDTO> getPortMappings() {
        return portMappings;
    }

    public Set<EnvironmentVariableDTO> getEnvironmentVariables() {
        return environmentVariables;
    }

    public static class Builder {

        private RunConfigurationDTO runConfiguration;
        private Set<String> aliases;
        private Set<LocationDTO> potentialLocations;
        private Set<PortMappingDTO> portMappings;
        private Set<EnvironmentVariableDTO> environmentVariables;

        public Builder withRunConfiguration(RunConfigurationDTO runConfiguration) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public Builder withAliases(Set<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        public Builder withPotentialLocations(Set<LocationDTO> potentialLocations) {
            this.potentialLocations = potentialLocations;
            return this;
        }

        public Builder withPortMappings(Set<PortMappingDTO> portMappings) {
            this.portMappings = portMappings;
            return this;
        }

        public Builder withEnvironmentVariables(Set<EnvironmentVariableDTO> environmentVariables) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public ContainerTemplateCreateDTO build() {
            return new ContainerTemplateCreateDTO(runConfiguration, aliases, potentialLocations, portMappings, environmentVariables);
        }
    }

}
