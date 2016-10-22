package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.ContainerInstance;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class EventHandler {

    private Set<EventListener> eventListeners = new HashSet<>();

    public void register( EventListener eventListener ) {
        eventListeners.add( eventListener );
    }

    public void onCreate( ContainerInstance instance ) {
        eventListeners.forEach( l -> l.onCreate( instance ) );
    }

    public void onUpdate( ContainerInstance instance ) {
        eventListeners.forEach( l -> l.onUpdate( instance ) );
    }

    public void onDelete( ContainerInstance instance ) {
        eventListeners.forEach( l -> l.onDelete( instance ) );
    }

}
