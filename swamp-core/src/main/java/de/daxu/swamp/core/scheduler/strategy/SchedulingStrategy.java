package de.daxu.swamp.core.scheduler.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SchedulingStrategy {

    Map<Container, Server> createSchedule( Set<Container> containers );
}
