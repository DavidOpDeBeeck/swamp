package de.daxu.swamp.scheduling.write.containerinstance.event;

import de.daxu.swamp.core.container.EnvironmentVariable;
import de.daxu.swamp.core.container.PortMapping;
import de.daxu.swamp.core.container.configuration.RunConfiguration;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;

import java.util.Date;
import java.util.Set;

public class ContainerInstanceScheduledEvent extends ContainerInstanceEvent {

    private final Date dateScheduled;
    private final String name;
    private final RunConfiguration runConfiguration;
    private final Set<PortMapping> portMappings;
    private final Set<EnvironmentVariable> environmentVariables;
    private final Server server;

    public ContainerInstanceScheduledEvent( ContainerInstanceId containerInstanceId,
                                            String name, RunConfiguration runConfiguration,
                                            Set<PortMapping> portMappings,
                                            Set<EnvironmentVariable> environmentVariables,
                                            Server server,
                                            Date dateScheduled ) {
        super( containerInstanceId );
        this.name = name;
        this.runConfiguration = runConfiguration;
        this.portMappings = portMappings;
        this.environmentVariables = environmentVariables;
        this.server = server;
        this.dateScheduled = dateScheduled;
    }

    public String getName() {
        return name;
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

    public Server getServer() {
        return server;
    }

    public Date getDateScheduled() {
        return dateScheduled;
    }
}
