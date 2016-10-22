package de.daxu.swamp.scheduler.event;

import de.daxu.swamp.scheduler.ContainerInstance;

public interface EventListener {

    void onCreate( ContainerInstance instance );

    void onUpdate( ContainerInstance instance );

    void onDelete( ContainerInstance instance );

}
