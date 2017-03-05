package de.daxu.swamp.deploy.notifier;

@FunctionalInterface
public interface ProgressNotifier<T> {

    void onProgress(T payload);

}
