package de.daxu.swamp.scheduler.action;

import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.scheduling.SchedulingService;
import de.daxu.swamp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionFactory {

    @Autowired
    private LocationService locationService;

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private SchedulingService schedulingService;

    public ClearNotManagedContainersAction clearNotManagedContainers() {
        return new ClearNotManagedContainersAction( eventHandler, schedulingService, locationService );
    }

    public SyncContainerInstanceAction syncContainerInstanceAction() {
        return new SyncContainerInstanceAction( eventHandler, schedulingService );
    }
}
