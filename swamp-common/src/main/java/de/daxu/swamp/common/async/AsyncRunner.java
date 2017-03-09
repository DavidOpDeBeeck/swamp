package de.daxu.swamp.common.async;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncRunner {

    private final TaskExecutor executor;

    public AsyncRunner(@Qualifier("taskExecutor") TaskExecutor executor) {
        this.executor = executor;
    }

    @Async
    public void run(Runnable runnable) {
        executor.execute(runnable);
    }
}
