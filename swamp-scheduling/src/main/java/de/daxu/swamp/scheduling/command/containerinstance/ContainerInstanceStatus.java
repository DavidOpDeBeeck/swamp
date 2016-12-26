package de.daxu.swamp.scheduling.command.containerinstance;

import java.util.Collections;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public enum ContainerInstanceStatus {
    INITIALIZED(),
    CREATED( INITIALIZED ),
    STARTED( CREATED ),
    STOPPED( STARTED ),
    REMOVED( CREATED, STARTED );

    private final Set<ContainerInstanceStatus> validPreviousStatuses = newHashSet();

    ContainerInstanceStatus( ContainerInstanceStatus... validPreviousStatuses ) {
        Collections.addAll( this.validPreviousStatuses, validPreviousStatuses );
    }

    public boolean isValidPreviousStatus( ContainerInstanceStatus previousStatus ) {
        return validPreviousStatuses.contains( previousStatus );
    }
}
