package de.daxu.swamp.scheduling.command.projectinstance.event;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;

import java.util.Date;
import java.util.Map;

public class ProjectInstanceInitializedEvent extends ProjectInstanceEvent {

    private final String name;
    private final String description;
    private final Map<ContainerInstanceId, Container> containers;
    private final Date dateInitialized;

    public ProjectInstanceInitializedEvent( ProjectInstanceId projectInstanceId,
                                            String name, String description, Map<ContainerInstanceId, Container> containers,
                                            Date dateInitialized ) {
        super( projectInstanceId );
        this.name = name;
        this.description = description;
        this.containers = containers;
        this.dateInitialized = dateInitialized;
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

    public Date getDateInitialized() {
        return dateInitialized;
    }
}
