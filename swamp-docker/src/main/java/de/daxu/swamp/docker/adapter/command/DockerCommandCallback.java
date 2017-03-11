package de.daxu.swamp.docker.adapter.command;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.core.async.ResultCallbackTemplate;
import de.daxu.swamp.deploy.notifier.DeployNotifier.CompletionNotifier;
import de.daxu.swamp.deploy.notifier.DeployNotifier.ErrorNotifier;
import de.daxu.swamp.deploy.notifier.DeployNotifier.ProgressNotifier;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DockerCommandCallback<T extends ResultCallback<S>, S>
        extends ResultCallbackTemplate<T, S> {

    private final Set<ProgressNotifier<S>> progressNotifiers;
    private final Set<ErrorNotifier> errorNotifiers;
    private final Set<CompletionNotifier> completionNotifiers;

    private DockerCommandCallback(Set<ProgressNotifier<S>> progressNotifiers,
                                  Set<ErrorNotifier> errorNotifiers,
                                  Set<CompletionNotifier> completionNotifiers) {
        this.progressNotifiers = progressNotifiers;
        this.errorNotifiers = errorNotifiers;
        this.completionNotifiers = completionNotifiers;
    }

    @Override
    public void onNext(S object) {
        progressNotifiers.forEach(callback -> callback.onProgress(object));
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        errorNotifiers.forEach(callback -> callback.onError(throwable));
    }

    @Override
    public void onComplete() {
        super.onComplete();
        completionNotifiers.forEach(CompletionNotifier::onCompletion);
    }

    public static class Builder<T extends ResultCallback<S>, S> {

        private final Set<ProgressNotifier<S>> progressNotifiers = newHashSet();
        private final Set<ErrorNotifier> errorNotifiers = newHashSet();
        private final Set<CompletionNotifier> completionNotifiers = newHashSet();

        public Builder<T, S> onNext(ProgressNotifier<S> progressNotifier) {
            this.progressNotifiers.add(progressNotifier);
            return this;
        }

        public Builder<T, S> onError(ErrorNotifier errorNotifier) {
            this.errorNotifiers.add(errorNotifier);
            return this;
        }

        public Builder<T, S> onComplete(CompletionNotifier completionNotifier) {
            this.completionNotifiers.add(completionNotifier);
            return this;
        }

        public DockerCommandCallback<T, S> build() {
            return new DockerCommandCallback<>(progressNotifiers, errorNotifiers, completionNotifiers);
        }
    }
}
