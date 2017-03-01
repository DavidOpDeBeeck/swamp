package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class CommandCallback<T extends ResultCallback<S>, S>
        extends ResultCallbackTemplate<T, S> {

    private final NextCallback<S> onNextDelegate;
    private final CompletedCallback onCompletedDelegate;

    private CommandCallback(NextCallback<S> onNextDelegate,
                            CompletedCallback onCompletedDelegate) {
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

        private NextCallback<S> nextCallback;
        private CompletedCallback completedCallback;

        public static Builder aCommandCallback() {
            return new Builder();
        }

        public Builder<T, S> withOnNextCallback(NextCallback<S> nextCallback) {
            this.nextCallback = nextCallback;
            return this;
        }

        public Builder<T, S> withOnCompletedCallback(CompletedCallback completedCallback) {
            this.completedCallback = completedCallback;
            return this;
        }

        public CommandCallback<T, S> build() {
            return new CommandCallback<>(nextCallback, completedCallback);
        }
    }
}
