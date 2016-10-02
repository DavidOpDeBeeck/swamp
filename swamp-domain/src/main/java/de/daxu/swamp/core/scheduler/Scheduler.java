package de.daxu.swamp.core.scheduler;

import de.daxu.swamp.core.container.Container;

public interface Scheduler {
    ContainerInstance schedule( Container container );
}
