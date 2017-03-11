package de.daxu.swamp.deploy;

import de.daxu.swamp.common.async.AsyncRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component
public class AsyncDeployExecutor {

    private final AsyncRunner asyncRunner;

    @Autowired
    public AsyncDeployExecutor(AsyncRunner asyncRunner) {
        this.asyncRunner = asyncRunner;
    }

    public <T> Execution<T> action(Supplier<DeployResult<T>> action) {
        return new Execution<>(asyncRunner, action);
    }

    public static class Execution<T> {

        private final Logger logger = LoggerFactory.getLogger(AsyncDeployExecutor.class);
        private final AsyncRunner asyncRunner;
        private final Supplier<DeployResult<T>> action;

        private Consumer<T> onSuccess;
        private Consumer<Set<String>> onFail;

        private Execution(AsyncRunner asyncRunner,
                          Supplier<DeployResult<T>> action) {
            this.asyncRunner = asyncRunner;
            this.action = action;
        }

        public Execution<T> onSuccess(Consumer<T> onSuccess) {
            this.onSuccess = onSuccess;
            return this;
        }

        public Execution<T> onFail(Consumer<Set<String>> onFail) {
            this.onFail = onFail;
            return this;
        }

        public void execute() {
            ListenableFuture<DeployResult<T>> future = asyncRunner.forCallable(action::get);

            future.addCallback(result -> {
                result.onSuccess(onSuccess);
                result.onFail(onFail);
            }, throwable -> logger.error("Execution encountered exception: {}", throwable.getMessage()));
        }
    }
}
