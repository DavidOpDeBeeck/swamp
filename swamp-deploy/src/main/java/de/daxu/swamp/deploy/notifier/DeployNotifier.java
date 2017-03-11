package de.daxu.swamp.deploy.notifier;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class DeployNotifier<T> {

    public static <T> DeployNotifier<T> forProgress(ProgressNotifier<T> progressNotifier) {
        return new DeployNotifier.Builder<T>()
                .withProgressNotifier(progressNotifier)
                .build();
    }

    private final Set<ProgressNotifier<T>> progressNotifiers;
    private final Set<ErrorNotifier> errorNotifiers;
    private final Set<CompletionNotifier> completionNotifiers;

    private DeployNotifier(Set<ProgressNotifier<T>> progressNotifiers,
                           Set<ErrorNotifier> errorNotifiers,
                           Set<CompletionNotifier> completionNotifiers) {
        this.errorNotifiers = errorNotifiers;
        this.progressNotifiers = progressNotifiers;
        this.completionNotifiers = completionNotifiers;
    }

    public void triggerProgress(T progress) {
        progressNotifiers.forEach(notifier -> notifier.onProgress(progress));
    }

    public void triggerError(Throwable throwable) {
        errorNotifiers.forEach(notifier -> notifier.onError(throwable));
    }

    public void triggerCompletion() {
        completionNotifiers.forEach(CompletionNotifier::onCompletion);
    }

    public static class Builder<T> {

        private Set<ProgressNotifier<T>> progressNotifiers = newHashSet();
        private Set<ErrorNotifier> errorNotifiers = newHashSet();
        private Set<CompletionNotifier> completionNotifiers = newHashSet();

        public Builder<T> withProgressNotifier(ProgressNotifier<T> notifier) {
            this.progressNotifiers.add(notifier);
            return this;
        }

        public Builder<T> withErrorNotifier(ErrorNotifier notifier) {
            this.errorNotifiers.add(notifier);
            return this;
        }

        public Builder<T> withCompletionNotifier(CompletionNotifier notifier) {
            this.completionNotifiers.add(notifier);
            return this;
        }

        public DeployNotifier<T> build() {
            return new DeployNotifier<>(progressNotifiers, errorNotifiers, completionNotifiers);
        }
    }

    @FunctionalInterface
    public interface ProgressNotifier<T> {

        void onProgress(T payload);
    }

    @FunctionalInterface
    public interface ErrorNotifier {

        void onError(Throwable throwable);
    }

    @FunctionalInterface
    public interface CompletionNotifier {

        void onCompletion();
    }
}
