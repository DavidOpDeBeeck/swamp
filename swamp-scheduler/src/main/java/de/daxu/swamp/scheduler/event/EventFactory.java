package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.core.ContainerInstance;
import org.springframework.stereotype.Component;

@Component
public class EventFactory {

    public ContainerInstanceEvent createEvent( ContainerInstance instance ) {
        return new ContainerInstanceEvent( instance );
    }
}
