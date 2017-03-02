package de.daxu.swamp.docker.callback;

@FunctionalInterface
public interface NextCallback<T> {

    void onNext(T object);

}
