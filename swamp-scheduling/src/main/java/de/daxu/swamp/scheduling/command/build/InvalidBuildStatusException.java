package de.daxu.swamp.scheduling.command.build;

import de.daxu.swamp.common.cqrs.ValidationException;

import static java.lang.String.format;

public class InvalidBuildStatusException extends ValidationException {

    InvalidBuildStatusException(BuildStatus currentStatus, BuildStatus statusToBe) {
        super(format("Build invalid status current %s to be %s.", currentStatus, statusToBe));
    }
}
