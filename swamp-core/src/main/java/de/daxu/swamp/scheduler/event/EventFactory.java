package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;
import de.daxu.swamp.scheduler.command.container.CreateCommand;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {

    public ContainerInstanceEvent createEvent( ContainerInstance instance ) {
        return new ContainerInstanceEvent( instance );
    }
}
