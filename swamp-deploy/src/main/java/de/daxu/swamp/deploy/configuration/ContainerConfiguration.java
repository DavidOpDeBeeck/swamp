package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.runconfiguration.RunConfiguration;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ContainerConfiguration {

    private RunConfiguration runConfiguration;
    private Set<PortMapping> portMappings;
    private Set<EnvironmentVariable> environmentVariables;

    private ContainerConfiguration( RunConfiguration runConfiguration,
                                    Set<PortMapping> portMappings,
                                    Set<EnvironmentVariable> environmentVariables ) {
        this.runConfiguration = runConfiguration;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public RunConfiguration getRunConfiguration() {
        return runConfiguration;
    }

    public Set<PortMapping> getPortMappings() {
        return portMappings;
    }

    public Set<EnvironmentVariable> getEnvironmentVariables() {
        return environmentVariables;
    }

    public static class Builder {

        private RunConfiguration runConfiguration;
        private Set<PortMapping> portMappings = newHashSet();
        private Set<EnvironmentVariable> environmentVariables = newHashSet();

        public static Builder aContainerConfiguration() {
            return new Builder();
        }

        public Builder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public Builder withPortMappings( Set<PortMapping> portMappings ) {
            this.portMappings = portMappings;
            return this;
        }

        public Builder withEnvironmentVariables( Set<EnvironmentVariable> environmentVariables ) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public ContainerConfiguration build() {
            return new ContainerConfiguration( runConfiguration, portMappings, environmentVariables );
        }
    }
}
