package de.daxu.swamp.scheduler.manager;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;
import java.util.Set;

public interface SchedulingManager {

    void schedule( Project project, SchedulingStrategy strategy );

    void schedule( ContainerInstance instance, Command command );

    Set<Project> getProjects();

    ContainerInstance getInstance( String internalId ); // TODO we limit the instances to a container to 1

    ContainerInstance getInstance( Container container ); // TODO we limit the instances to a container to 1

    Set<ContainerInstance> getAllInstances();
}
