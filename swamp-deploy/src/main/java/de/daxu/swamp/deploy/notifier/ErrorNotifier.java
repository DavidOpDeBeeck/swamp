package de.daxu.swamp.deploy.notifier;

@FunctionalInterface
public interface ErrorNotifier {

    void onError(Throwable throwable);

}
