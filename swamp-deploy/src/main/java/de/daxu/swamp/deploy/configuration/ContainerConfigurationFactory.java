package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.runconfiguration.RunConfiguration;
import org.springframework.stereotype.Component;

import java.util.Set;

import static de.daxu.swamp.deploy.configuration.ContainerConfiguration.Builder.aContainerConfiguration;

@Component
public class ContainerConfigurationFactory {

    public ContainerConfiguration createCongfiguration( RunConfiguration runConfiguration,
                                                        Set<PortMapping> portMappings,
                                                        Set<EnvironmentVariable> environmentVariables ) {
        return aContainerConfiguration()
                .withRunConfiguration( runConfiguration )
                .withPortMappings( portMappings )
                .withEnvironmentVariables( environmentVariables )
                .build();
    }
}
