package de.daxu.swamp.scheduling.command.containerinstance;

import de.daxu.swamp.common.cqrs.ValidationException;

import static java.lang.String.format;

public class InvalidContainerStatusException extends ValidationException {

    InvalidContainerStatusException(ContainerInstanceStatus currentStatus, ContainerInstanceStatus statusToBe) {
        super(format("Container instance invalid status current %s to be %s.", currentStatus, statusToBe));
    }
}
