package de.daxu.swamp.core.scheduler;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;
import java.util.Map;

public interface Scheduler {

    void schedule( Project project, SchedulingStrategy strategy );

    Collection<Project> getProjects();

    Collection<ContainerInstance> getInstances( Project project );

    void updateInternalMap( Map<String, ContainerInstance> map );

    Map<String, ContainerInstance> getInternalMap();
}
