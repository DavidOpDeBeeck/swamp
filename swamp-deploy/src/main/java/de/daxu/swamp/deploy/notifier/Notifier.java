package de.daxu.swamp.deploy.notifier;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Notifier<T> {

    private final Set<ProgressNotifier<T>> progressNotifiers;
    private final Set<ErrorNotifier> errorNotifiers;
    private final Set<CompletionNotifier> completionNotifiers;

    private Notifier(Set<ProgressNotifier<T>> progressNotifiers,
                     Set<ErrorNotifier> errorNotifiers,
                     Set<CompletionNotifier> completionNotifiers) {
        this.errorNotifiers = errorNotifiers;
        this.progressNotifiers = progressNotifiers;
        this.completionNotifiers = completionNotifiers;
    }

    public void onProgress(T progress) {
        progressNotifiers.forEach(notifier -> notifier.onProgress(progress));
    }

    public void onError(Throwable throwable) {
        errorNotifiers.forEach(notifier -> notifier.onError(throwable));
    }

    public void onCompletion() {
        completionNotifiers.forEach(CompletionNotifier::onCompletion);
    }

    public Set<ProgressNotifier<T>> getProgressNotifiers() {
        return progressNotifiers;
    }

    public Set<ErrorNotifier> getErrorNotifiers() {
        return errorNotifiers;
    }

    public Set<CompletionNotifier> getCompletionNotifiers() {
        return completionNotifiers;
    }

    public static class Builder<T> {

        private Set<ProgressNotifier<T>> progressNotifiers = newHashSet();
        private Set<ErrorNotifier> errorNotifiers = newHashSet();
        private Set<CompletionNotifier> completionNotifiers = newHashSet();

        public Builder<T> withNextNotifier(ProgressNotifier<T> progressNotifier) {
            this.progressNotifiers.add(progressNotifier);
            return this;
        }

        public Builder<T> withErrorNotifier(ErrorNotifier errorNotifier) {
            this.errorNotifiers.add(errorNotifier);
            return this;
        }

        public Builder<T> withCompletionNotifier(CompletionNotifier completionNotifier) {
            this.completionNotifiers.add(completionNotifier);
            return this;
        }

        public Notifier<T> build() {
            return new Notifier<>(progressNotifiers, errorNotifiers, completionNotifiers);
        }
    }
}
