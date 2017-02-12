package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.cqrs.ValidationException;

import static java.lang.String.format;

public class InvalidContainerStatusChangeException extends ValidationException {

    InvalidContainerStatusChangeException(ContainerInstanceStatus currentStatus, ContainerInstanceStatus invalidStatus) {
        super(format("Container instance invalid status change from %s to %s.", currentStatus, invalidStatus));
    }
}
