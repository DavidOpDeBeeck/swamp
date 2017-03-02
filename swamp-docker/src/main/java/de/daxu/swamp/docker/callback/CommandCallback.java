package de.daxu.swamp.docker.callback;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class CommandCallback<T extends ResultCallback<S>, S>
        extends ResultCallbackTemplate<T, S> {

    private final Set<ErrorCallback> errorCallbacks;
    private final Set<NextCallback<S>> nextCallbacks;
    private final Set<CompletedCallback> completedCallbacks;

    private CommandCallback(Set<ErrorCallback> errorCallbacks,
                            Set<NextCallback<S>> nextCallbacks,
                            Set<CompletedCallback> completedCallbacks) {
        this.errorCallbacks = errorCallbacks;
        this.nextCallbacks = nextCallbacks;
        this.completedCallbacks = completedCallbacks;
    }

    @Override
    public void onNext(S object) {
        nextCallbacks.forEach(callback -> callback.onNext(object));
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        errorCallbacks.forEach(callback -> callback.onError(throwable));
    }

    @Override
    public void onComplete() {
        super.onComplete();
        completedCallbacks.forEach(CompletedCallback::onCompleted);
    }

    public static class Builder<T extends ResultCallback<S>, S> {

        private final Set<ErrorCallback> errorCallbacks = newHashSet();
        private final Set<NextCallback<S>> nextCallbacks = newHashSet();
        private final Set<CompletedCallback> completedCallbacks = newHashSet();

        public static Builder aCommandCallback() {
            return new Builder();
        }

        public Builder<T, S> onNext(NextCallback<S> nextCallback) {
            this.nextCallbacks.add(nextCallback);
            return this;
        }

        public Builder<T, S> onError(ErrorCallback errorCallback) {
            this.errorCallbacks.add(errorCallback);
            return this;
        }

        public Builder<T, S> onCompleted(CompletedCallback completedCallback) {
            this.completedCallbacks.add(completedCallback);
            return this;
        }

        public CommandCallback<T, S> build() {
            return new CommandCallback<>(errorCallbacks, nextCallbacks, completedCallbacks);
        }
    }
}
