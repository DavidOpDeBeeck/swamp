package de.daxu.swamp.scheduling.command.projectinstance.event;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;

import java.time.LocalDateTime;
import java.util.Map;

public class ProjectInstanceInitializedEvent extends ProjectInstanceEvent {

    private final String name;
    private final String description;
    private final Map<ContainerInstanceId, Container> containers;
    private final LocalDateTime initializedAt;

    public ProjectInstanceInitializedEvent( ProjectInstanceId projectInstanceId,
                                            String name, String description, Map<ContainerInstanceId, Container> containers,
                                            LocalDateTime initializedAt ) {
        super( projectInstanceId );
        this.name = name;
        this.description = description;
        this.containers = containers;
        this.initializedAt = initializedAt;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<ContainerInstanceId, Container> getContainers() {
        return containers;
    }

    public LocalDateTime getInitializedAt() {
        return initializedAt;
    }
}
