package de.daxu.swamp.scheduling.write.containerinstance;

import static java.lang.String.format;

public class InvalidContainerStatusChangeException extends RuntimeException {

    InvalidContainerStatusChangeException( ContainerInstanceStatus currentStatus, ContainerInstanceStatus invalidStatus ) {
        super( format( "Container instance invalid status change from %s to %s.", currentStatus, invalidStatus ) );
    }
}
