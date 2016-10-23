package de.daxu.swamp.scheduler.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;

import java.util.Map;
import java.util.Set;

public class WeightStrategy implements SchedulingStrategy {

    @Override
    public Map<Container, Server> createSchedule( Set<Container> containers ) {
        return null;
    }
    // TODO: when location has a weight this can be implemented
}
