package de.daxu.swamp.common.cqrs;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
