package de.daxu.swamp.deploy.configuration;

import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.deploy.container.ContainerId;
import org.springframework.stereotype.Component;

import java.util.Set;

import static de.daxu.swamp.deploy.configuration.ContainerConfiguration.Builder.aContainerConfiguration;
import static de.daxu.swamp.deploy.configuration.CreateContainerConfiguration.Builder.aCreateContainerConfiguration;

@Component
public class ContainerConfigurationFactory {

    public ContainerConfiguration createCongfiguration( ContainerId containerId,
                                                        Server server ) {
        return aContainerConfiguration()
                .withContainerId( containerId )
                .withServer( server )
                .build();
    }

    public CreateContainerConfiguration createCreateConfiguration( ContainerId containerId,
                                                                   Server server, RunConfiguration runConfiguration,
                                                                   Set<PortMapping> portMappings,
                                                                   Set<EnvironmentVariable> environmentVariables ) {
        return aCreateContainerConfiguration()
                .withContainerId( containerId )
                .withRunConfiguration( runConfiguration )
                .withPortMappings( portMappings )
                .withEnvironmentVariables( environmentVariables )
                .build();
    }


}
