package de.daxu.swamp.scheduling.task.statuscheck;

import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;

public interface StatusChangeCheck {

    boolean check( ContainerInstanceView containerInstanceView );

}
