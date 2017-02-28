package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class CommandCallback<T extends ResultCallback<S>, S>
        extends ResultCallbackTemplate<T, S> {

    private final OnNextCallback<S> onNextDelegate;
    private final OnCompletedCallback onCompletedDelegate;

    private CommandCallback(OnNextCallback<S> onNextDelegate,
                            OnCompletedCallback onCompletedDelegate) {
        this.onNextDelegate = onNextDelegate;
        this.onCompletedDelegate = onCompletedDelegate;
    }

    @Override
    public void onNext(S object) {
        if(onNextDelegate != null) {
            onNextDelegate.onNext(object);
        }
    }

    @Override
    public void onComplete() {
        super.onComplete();
        if(onCompletedDelegate != null) {
            onCompletedDelegate.onComplete();
        }
    }

    public static class Builder<T extends ResultCallback<S>, S> {

        private OnNextCallback<S> onNextCallback;
        private OnCompletedCallback onCompletedCallback;

        public static Builder aCommandCallback() {
            return new Builder();
        }

        public Builder<T, S> withOnNextCallback(OnNextCallback<S> onNextCallback) {
            this.onNextCallback = onNextCallback;
            return this;
        }

        public Builder<T, S> withOnCompletedCallback(OnCompletedCallback onCompletedCallback) {
            this.onCompletedCallback = onCompletedCallback;
            return this;
        }

        public CommandCallback<T, S> build() {
            return new CommandCallback<>(onNextCallback, onCompletedCallback);
        }
    }
}
