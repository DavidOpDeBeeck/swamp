package de.daxu.swamp.scheduling.write.containerinstance;

import static java.lang.String.format;

public class InvalidContainerStatusException extends RuntimeException {

    InvalidContainerStatusException( ContainerInstanceStatus currentStatus, ContainerInstanceStatus statusToBe ) {
        super( format( "Container instance invalid status current %s to be %s.", currentStatus, statusToBe ) );
    }
}
