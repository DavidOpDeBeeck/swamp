package de.daxu.swamp.core.startable;

public interface Startable {

    void start();

    void stop();

    void resume();

    void remove();

    StartableStatus getStatus();
}
