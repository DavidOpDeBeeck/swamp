package de.daxu.swamp.core.scheduler;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;
import java.util.Map;

public interface Scheduler {

    void schedule( Project project, SchedulingStrategy strategy );

    void start( ContainerInstance instance );

    void stop( ContainerInstance instance );

    void restart( ContainerInstance instance );

    // TODO: SchedulerManager
    Collection<Project> getProjects();

    // TODO: SchedulerManager
    Collection<ContainerInstance> getInstances( Project project );

    void updateInternalMap( Map<String, ContainerInstance> map );

    Map<String, ContainerInstance> getInternalMap();
}
