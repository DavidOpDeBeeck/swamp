package de.daxu.swamp.docker.command;

@FunctionalInterface
public interface OnNextCallback<T> {

    void onNext(T object);

}
