package de.daxu.swamp.core.scheduler.manager;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.command.instance.ContainerInstanceCommand;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;

public interface SchedulingManager {

    void schedule( Project project, SchedulingStrategy strategy );

    void schedule( ContainerInstance instance, ContainerInstanceCommand command );

    Collection<Project> getProjects();

    Collection<ContainerInstance> getInstances( Project project );
}
