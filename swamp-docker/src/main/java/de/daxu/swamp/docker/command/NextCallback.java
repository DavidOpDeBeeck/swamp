package de.daxu.swamp.docker.command;

@FunctionalInterface
public interface NextCallback<T> {

    void onNext(T object);

}
