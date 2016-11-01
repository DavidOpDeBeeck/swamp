package de.daxu.swamp.scheduler;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.core.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.strategy.SchedulingStrategy;

public interface Scheduler {

    void schedule( Project project, SchedulingStrategy strategy );

    void schedule( ContainerInstance instance, Command command );
}
