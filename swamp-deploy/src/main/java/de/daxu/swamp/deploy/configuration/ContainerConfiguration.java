package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.configuration.RunConfiguration;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.deploy.group.GroupId;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.deploy.configuration.ContainerConfiguration.Builder.aContainerConfiguration;

public class ContainerConfiguration {

    public static ContainerConfiguration of(GroupId groupId, Container container) {
        return aContainerConfiguration()
                .withGroup(groupId)
                .withAliases(container.getAliases())
                .withPortMappings(container.getPortMappings())
                .withEnvironmentVariables(container.getEnvironmentVariables())
                .withRunConfiguration(container.getRunConfiguration()).build();
    }

    private final GroupId group;
    private final Set<String> aliases;
    private final RunConfiguration runConfiguration;
    private final Set<PortMapping> portMappings;
    private final Set<EnvironmentVariable> environmentVariables;

    private ContainerConfiguration(GroupId group,
                                   Set<String> aliases,
                                   RunConfiguration runConfiguration,
                                   Set<PortMapping> portMappings,
                                   Set<EnvironmentVariable> environmentVariables) {
        this.group = group;
        this.aliases = aliases;
        this.runConfiguration = runConfiguration;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
    }

    public GroupId getGroup() {
        return group;
    }

    public Set<String> getAliases() {
        return aliases;
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
        private Set<String> aliases;
        private RunConfiguration runConfiguration;
        private Set<PortMapping> portMappings = newHashSet();
        private Set<EnvironmentVariable> environmentVariables = newHashSet();

        public static Builder aContainerConfiguration() {
            return new Builder();
        }

        public Builder withGroup(GroupId group) {
            this.group = group;
            return this;
        }

        public Builder withAliases(Set<String> aliases) {
            this.aliases = aliases;
            return this;
        }

        public Builder withRunConfiguration(RunConfiguration runConfiguration) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public Builder withPortMappings(Set<PortMapping> portMappings) {
            this.portMappings = portMappings;
            return this;
        }

        public Builder withEnvironmentVariables(Set<EnvironmentVariable> environmentVariables) {
            this.environmentVariables = environmentVariables;
            return this;
        }

        public ContainerConfiguration build() {
            return new ContainerConfiguration(group, aliases, runConfiguration, portMappings, environmentVariables);
        }
    }
}
