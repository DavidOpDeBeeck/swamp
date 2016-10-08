package de.daxu.swamp.core.scheduler.strategy;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;

import java.util.List;
import java.util.Map;

public class WeightStrategy implements SchedulingStrategy {

    @Override
    public Map<Container, Server> createSchedule( List<Container> containers ) {
        return null;
    }
    // TODO: when location has a weight this can be implemented
}
