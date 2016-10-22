package de.daxu.swamp.scheduler.action;

import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.manager.SchedulingManager;
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
    private SchedulingManager schedulingManager;

    public ClearNotManagedContainersAction clearNotManagedContainers() {
        return new ClearNotManagedContainersAction( eventHandler, schedulingManager, locationService );
    }

    public SyncContainerInstanceAction syncContainerInstanceAction() {
        return new SyncContainerInstanceAction( eventHandler, schedulingManager );
    }
}
