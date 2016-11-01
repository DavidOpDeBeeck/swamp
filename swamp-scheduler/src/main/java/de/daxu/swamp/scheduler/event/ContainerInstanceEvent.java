package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.core.ProjectInstance;

public class ContainerInstanceEvent {

    // TODO add metadata

    private ContainerInstance containerInstance;

    public ContainerInstanceEvent( ContainerInstance containerInstance ) {
        this.containerInstance = containerInstance;
    }

    public ContainerInstance getContainerInstance() {
        return containerInstance;
    }

    public ProjectInstance getProjectInstance() {
        return containerInstance.getProjectInstance();
    }
}
