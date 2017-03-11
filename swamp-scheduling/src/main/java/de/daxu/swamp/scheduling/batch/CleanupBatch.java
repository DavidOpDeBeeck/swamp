package de.daxu.swamp.scheduling.batch;

import de.daxu.swamp.scheduling.batch.containerinstance.ContainerInstanceBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupBatch {

    private static final int INDIVIDUAL_TASK_DELAY = 120000;

    private final ContainerInstanceBatchService containerInstanceBatchService;

    @Autowired
    public CleanupBatch(ContainerInstanceBatchService containerInstanceBatchService) {
        this.containerInstanceBatchService = containerInstanceBatchService;
    }

    @Scheduled(fixedDelay = INDIVIDUAL_TASK_DELAY)
    public void run() {
        this.containerInstanceBatchService.removeExpiredInitializedAndCreationContainers();
        this.containerInstanceBatchService.removeExpiredCreatedContainers();
        this.containerInstanceBatchService.stopNotRunningStartedContainers();
        this.containerInstanceBatchService.removeExpiredStoppedContainers();
    }
}
