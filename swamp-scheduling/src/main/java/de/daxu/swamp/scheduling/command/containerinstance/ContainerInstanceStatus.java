package de.daxu.swamp.scheduling.command.containerinstance;

import com.google.common.collect.ImmutableMap;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public enum ContainerInstanceStatus {

    INITIALIZED,
    CREATED,
    STARTED,
    STOPPED,
    REMOVED;

    private static final ImmutableMap<ContainerInstanceStatus, Set<ContainerInstanceStatus>> validPreviousStatuses
            = new ImmutableMap.Builder<ContainerInstanceStatus, Set<ContainerInstanceStatus>>()
            .put( INITIALIZED, newHashSet() )
            .put( CREATED, newHashSet( INITIALIZED ) )
            .put( STARTED, newHashSet( CREATED, STOPPED ) )
            .put( STOPPED, newHashSet( STARTED ) )
            .put( REMOVED, newHashSet( INITIALIZED, CREATED, STARTED, STOPPED ) )
            .build();

    public static boolean isValidPreviousStatus( ContainerInstanceStatus status, ContainerInstanceStatus previousStatus ) {
        return validPreviousStatuses.get( status ).contains( previousStatus );
    }
}
