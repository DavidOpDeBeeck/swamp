package de.daxu.swamp.scheduler.event;

public interface EventListener {

    void onCreate( ContainerInstanceEvent event );

    void onUpdate( ContainerInstanceEvent instance );

    void onDelete( ContainerInstanceEvent instance );

}
