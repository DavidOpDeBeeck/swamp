package de.daxu.swamp.docker.command;

@FunctionalInterface
public interface OnCompletedCallback {

    void onComplete();

}
