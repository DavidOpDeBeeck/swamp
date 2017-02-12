package de.daxu.swamp.scheduling.batch;

import de.daxu.swamp.scheduling.batch.containerinstance.ContainerInstanceBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupBatch {

    private static final int INDIVIDUAL_TASK_DELAY = 15000;

    private final Logger logger = LoggerFactory.getLogger(CleanupBatch.class);
    private final ContainerInstanceBatchService containerInstanceBatchService;

    @Autowired
    public CleanupBatch(ContainerInstanceBatchService containerInstanceBatchService) {
        this.containerInstanceBatchService = containerInstanceBatchService;
    }

    @Scheduled(fixedDelay = INDIVIDUAL_TASK_DELAY)
    public void run() {
        logger.info("Starting CleanupBatch");
        this.containerInstanceBatchService.removeExpiredInitializedContainers();
        this.containerInstanceBatchService.removeExpiredCreatedContainers();
        this.containerInstanceBatchService.stopNotRunningStartedContainers();
        this.containerInstanceBatchService.removeExpiredStoppedContainers();
        logger.info("Stopping CleanupBatch");
    }
}
