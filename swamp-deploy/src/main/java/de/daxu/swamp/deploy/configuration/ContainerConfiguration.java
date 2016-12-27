package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.deploy.group.GroupId;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ContainerConfiguration {

    private final GroupId group;
    private final RunConfiguration runConfiguration;
    private final Set<PortMapping> portMappings;
    private final Set<EnvironmentVariable> environmentVariables;

    private ContainerConfiguration( GroupId group,
                                    RunConfiguration runConfiguration,
                                    Set<PortMapping> portMappings,
                                    Set<EnvironmentVariable> environmentVariables ) {
        this.group = group;
        this.runConfiguration = runConfiguration;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public GroupId getGroup() {
        return group;
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

        private GroupId group;
        private RunConfiguration runConfiguration;
        private Set<PortMapping> portMappings = newHashSet();
        private Set<EnvironmentVariable> environmentVariables = newHashSet();

        public static Builder aContainerConfiguration() {
            return new Builder();
        }

        public Builder withGroup( GroupId group ) {
            this.group = group;
            return this;
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
            return new ContainerConfiguration( group, runConfiguration, portMappings, environmentVariables );
        }
    }
}
