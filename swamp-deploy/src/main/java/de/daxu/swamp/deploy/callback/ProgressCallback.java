package de.daxu.swamp.deploy.callback;

@FunctionalInterface
public interface ProgressCallback<T> {

    void onProgress(T progress);
}
