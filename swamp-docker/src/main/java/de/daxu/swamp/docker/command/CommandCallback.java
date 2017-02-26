package de.daxu.swamp.docker.command;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.core.async.ResultCallbackTemplate;

public class CommandCallback<T extends ResultCallback<S>, S>
        extends ResultCallbackTemplate<T, S> {

    private static final OnNextCallback DEFAULT_NEXT_CALLBACK = (o) -> {};
    private static final OnCompletedCallback DEFAULT_COMPLETED_CALLBACK = () -> {};

    private final OnNextCallback<S> onNextDelegate;
    private final OnCompletedCallback onCompletedDelegate;

    CommandCallback(OnNextCallback<S> onNextDelegate) {
        this(onNextDelegate, DEFAULT_COMPLETED_CALLBACK);
    }

    @SuppressWarnings("unchecked")
    CommandCallback(OnCompletedCallback onCompletedDelegate) {
        this(DEFAULT_NEXT_CALLBACK, onCompletedDelegate);
    }

    private CommandCallback(OnNextCallback<S> onNextDelegate,
                            OnCompletedCallback onCompletedDelegate) {
        this.onNextDelegate = onNextDelegate;
        this.onCompletedDelegate = onCompletedDelegate;
    }

    @Override
    public void onNext(S object) {
        onNextDelegate.onNext(object);
    }

    @Override
    public void onComplete() {
        super.onComplete();
        onCompletedDelegate.onComplete();
    }
}
