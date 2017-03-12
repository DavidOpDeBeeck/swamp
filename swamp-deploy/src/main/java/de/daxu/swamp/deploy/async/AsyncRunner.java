package de.daxu.swamp.deploy.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

@Component
public class AsyncRunner {

    private final Executor executor;

    @Autowired
    public AsyncRunner(@Qualifier("taskExecutor") Executor executor) {
        this.executor = executor;
    }

    @Async
    public void forRunnable(Runnable runnable) {
        executor.execute(runnable);
    }

    @Async
    public <T> ListenableFuture<T> forCallable(Callable<T> callable) {
        ListenableFutureTask<T> task = new ListenableFutureTask<>(callable);
        executor.execute(task);
        return task;
    }
}
