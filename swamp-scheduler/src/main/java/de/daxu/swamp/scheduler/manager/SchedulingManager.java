package de.daxu.swamp.scheduler.manager;

import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.command.Command;
import de.daxu.swamp.scheduler.strategy.SchedulingStrategy;

public interface SchedulingManager {

    void schedule( Project project, SchedulingStrategy strategy );

    void schedule( ContainerInstance instance, Command command );
}
