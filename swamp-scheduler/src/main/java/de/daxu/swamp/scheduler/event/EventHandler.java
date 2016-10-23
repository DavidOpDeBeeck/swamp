package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventHandler {

    @Autowired
    private EventFactory eventFactory;

    private Set<EventListener> eventListeners = new HashSet<>();

    public void register( EventListener eventListener ) {
        eventListeners.add( eventListener );
    }

    public void onCreate( ContainerInstance containerInstance ) {
        eventListeners.forEach( l -> l.onCreate( eventFactory.createEvent( containerInstance ) ) );
    }

    public void onUpdate( ContainerInstance instance ) {
        eventListeners.forEach( l -> l.onUpdate( eventFactory.createEvent( instance ) ) );
    }

    public void onDelete( ContainerInstance instance ) {
        eventListeners.forEach( l -> l.onDelete( eventFactory.createEvent( instance ) ) );
    }

}
