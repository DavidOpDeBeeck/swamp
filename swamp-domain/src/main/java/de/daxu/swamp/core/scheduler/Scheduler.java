package de.daxu.swamp.core.scheduler;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;

public interface Scheduler {

    void schedule( Project project, SchedulingStrategy strategy );

    Collection<Project> getProjects();

    Collection<ContainerInstance> getInstances( Project project );
}
