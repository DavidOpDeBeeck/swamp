package de.daxu.swamp.workspace;

class WorkspaceException extends RuntimeException {

    WorkspaceException(String message) {
        super(message);
    }

    WorkspaceException(Throwable cause) {
        super(cause);
    }

    WorkspaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
