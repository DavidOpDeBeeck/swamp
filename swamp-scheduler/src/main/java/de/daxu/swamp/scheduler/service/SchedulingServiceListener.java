package de.daxu.swamp.scheduler.service;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;
import de.daxu.swamp.scheduler.event.ContainerInstanceEvent;
import de.daxu.swamp.scheduler.event.EventHandler;
import de.daxu.swamp.scheduler.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SchedulingServiceListener implements EventListener {

    @Autowired
    private EventHandler eventHandler;

    @Autowired
    private SchedulingService schedulingService;

    @PostConstruct
    private void setUp() {
        eventHandler.register( this );
    }

    @Override
    public void onCreate( ContainerInstanceEvent event ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( event.getProjectInstance().getId() );
        ContainerInstance containerInstance = event.getContainerInstance();

        projectInstance.addContainerInstance( containerInstance );
        schedulingService.updateProjectInstance( projectInstance );
    }

    @Override
    public void onUpdate( ContainerInstanceEvent event ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( event.getProjectInstance().getId() );
        ContainerInstance containerInstance = event.getContainerInstance();

        projectInstance.updateContainerInstance( containerInstance );
        schedulingService.updateProjectInstance( projectInstance );
    }

    @Override
    public void onDelete( ContainerInstanceEvent event ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( event.getProjectInstance().getId() );
        ContainerInstance containerInstance = event.getContainerInstance();

        projectInstance.removeContainerInstance( containerInstance );
        schedulingService.updateProjectInstance( projectInstance );
    }
}
