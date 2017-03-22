package de.daxu.swamp.api.containertemplate.dto;

import de.daxu.swamp.api.configuration.dto.RunConfigurationDTO;
import de.daxu.swamp.api.location.dto.LocationDTO;
import de.daxu.swamp.common.ValueObject;

import java.util.Set;

public class ContainerTemplateDTO extends ValueObject {

    private String id;
    private Set<String> aliases;
    private RunConfigurationDTO runConfiguration;
    private Set<LocationDTO> potentialLocations;
    private Set<PortMappingDTO> portMappings;
    private Set<EnvironmentVariableDTO> environmentVariables;

    @SuppressWarnings("unused")
    private ContainerTemplateDTO() {
    }

    private ContainerTemplateDTO(String id,
                                 Set<String> aliases,
                                 RunConfigurationDTO runConfiguration,
                                 Set<LocationDTO> potentialLocations,
                                 Set<PortMappingDTO> portMappings,
                                 Set<EnvironmentVariableDTO> environmentVariables) {
        this.id = id;
        this.aliases = aliases;
        this.runConfiguration = runConfiguration;
        this.potentialLocations = potentialLocations;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public String getId() {
        return id;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    public RunConfigurationDTO getRunConfiguration() {
        return runConfiguration;
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

        private String id;
        private Set<String> aliases;
        private RunConfigurationDTO runConfiguration;
        private Set<LocationDTO> potentialLocations;
        private Set<PortMappingDTO> portMappings;
        private Set<EnvironmentVariableDTO> environmentVariables;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withAliases(Set<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        public Builder withRunConfiguration(RunConfigurationDTO runConfiguration) {
            this.runConfiguration = runConfiguration;
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

        public ContainerTemplateDTO build() {
            return new ContainerTemplateDTO(id, aliases, runConfiguration, potentialLocations, portMappings, environmentVariables);
        }
    }
}
