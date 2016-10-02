package de.daxu.swamp.core.startable;

public class StartableStateException extends RuntimeException {

    public StartableStateException( String message ) {
        super( message );
    }

    public StartableStateException( String message, Throwable cause ) {
        super( message, cause );
    }

    public StartableStateException( Throwable cause ) {
        super( cause );
    }
}
