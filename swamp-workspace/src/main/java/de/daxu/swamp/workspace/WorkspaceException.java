package de.daxu.swamp.workspace;

public class WorkspaceException extends RuntimeException {

    public WorkspaceException(String message) {
        super(message);
    }

    WorkspaceException(Throwable cause) {
        super(cause);
    }

}
