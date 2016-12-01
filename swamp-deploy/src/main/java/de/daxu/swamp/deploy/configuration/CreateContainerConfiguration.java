package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.deploy.response.ContainerResponse;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class CreateContainerConfiguration extends Configuration {

    private RunConfiguration runConfiguration;
    private Set<PortMapping> portMappings;
    private Set<EnvironmentVariable> environmentVariables;

    private CreateContainerConfiguration( ContainerId containerId,
                                          Server server,
                                          RunConfiguration runConfiguration,
                                          Set<PortMapping> portMappings,
                                          Set<EnvironmentVariable> environmentVariables ) {
        super( containerId, server );
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

    public static class Builder extends Configuration.Builder<Builder> {

        private RunConfiguration runConfiguration;
        private Set<PortMapping> portMappings = newHashSet();
        private Set<EnvironmentVariable> environmentVariables = newHashSet();

        public static ContainerResponse.Builder aCreateContainerConfiguration() {
            return new ContainerResponse.Builder();
        }


        public Builder withRunConfiguration( RunConfiguration runConfiguration ) {
            this.runConfiguration = runConfiguration;
            return this;
        }

        public Builder withPortMappings( PortMapping... portMappings ) {
            Collections.addAll( this.portMappings, portMappings );
            return this;
        }

        public Builder withEnvironmentVariables( EnvironmentVariable... environmentVariables ) {
            Collections.addAll( this.environmentVariables, environmentVariables );
            return this;
        }

        @Override
        public CreateContainerConfiguration build() {
            return new CreateContainerConfiguration( containerId, server, runConfiguration, portMappings, environmentVariables );
        }
    }
}
