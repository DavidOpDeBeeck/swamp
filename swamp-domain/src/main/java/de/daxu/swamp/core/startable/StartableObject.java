package de.daxu.swamp.core.startable;

public class StartableObject implements Startable {

    private StartableStatus status;

    public StartableObject() {
        status = StartableStatus.CREATED;
    }

    public void start() {
        if ( status != StartableStatus.CREATED )
            throw new StartableStateException( "Expected CREATED was " + status );
        status = StartableStatus.STARTED;
    }

    public void stop() {
        if ( status != StartableStatus.STARTED && status != StartableStatus.RESUMED )
            throw new StartableStateException( "Expected STARTED or RESUMED was " + status );
        status = StartableStatus.STOPPED;
    }

    public void resume() {
        if ( status != StartableStatus.STOPPED )
            throw new StartableStateException( "Expected STOPPED was " + status );
        status = StartableStatus.RESUMED;
    }

    public void remove() {
        if ( status != StartableStatus.STOPPED )
            throw new StartableStateException( "Expected STOPPED was " + status );
        status = StartableStatus.REMOVED;
    }

    public StartableStatus getStatus() {
        return status;
    }
}
