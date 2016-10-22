package de.daxu.swamp.core.scheduler;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.scheduler.strategy.SchedulingStrategy;

import java.util.Collection;
import java.util.Map;

public interface Scheduler {

    ContainerInstance schedule( Container container, Server server );

    void updateInternalMap( Map<String, ContainerInstance> map );

    Map<String, ContainerInstance> getInternalMap();
}
