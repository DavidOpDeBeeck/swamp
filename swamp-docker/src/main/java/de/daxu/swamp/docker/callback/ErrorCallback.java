package de.daxu.swamp.docker.callback;

@FunctionalInterface
public interface ErrorCallback {

    void onError(Throwable throwable);

}
