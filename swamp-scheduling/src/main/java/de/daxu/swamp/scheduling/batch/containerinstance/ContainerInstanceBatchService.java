package de.daxu.swamp.scheduling.batch.containerinstance;

import de.daxu.swamp.common.validator.BasicValidator;
import de.daxu.swamp.common.validator.Validator;
import de.daxu.swamp.common.validator.WaitTimeExpiredValidator;
import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployClient;
import de.daxu.swamp.deploy.DeployClientManager;
import de.daxu.swamp.deploy.DeployResult;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason.NOT_RUNNING_ON_HOST;

@Component
public class ContainerInstanceBatchService {

    private static final int CREATED_FAILED_WAIT_TIME = 60;
    private static final int INIT_FAILED_WAIT_TIME = 60 * 5;
    private static final int STARTED_FAILED_WAIT_TIME = 60;
    private static final int STOPPED_FAILED_WAIT_TIME = 30;

    private final Logger logger = LoggerFactory.getLogger(ContainerInstanceBatchService.class);

    private final DeployClientManager clientManager;
    private final LocationService locationService;
    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    private final Validator<ContainerInstanceView> initTimeExpiredValidator
            = new WaitTimeExpiredValidator<>(INIT_FAILED_WAIT_TIME, ContainerInstanceView::getInitializedAt);

    private final Validator<ContainerInstanceView> createdTimeExpiredValidator
            = new WaitTimeExpiredValidator<>(CREATED_FAILED_WAIT_TIME, ContainerInstanceView::getCreatedAt);

    private final Validator<ContainerInstanceView> startedTimeExpiredValidator
            = new WaitTimeExpiredValidator<>(STARTED_FAILED_WAIT_TIME, ContainerInstanceView::getStartedAt);

    private final Validator<ContainerInstanceView> stoppedTimeExpiredValidator
            = new WaitTimeExpiredValidator<>(STOPPED_FAILED_WAIT_TIME, ContainerInstanceView::getStoppedAt);

    @Autowired
    public ContainerInstanceBatchService(DeployClientManager clientManager,
                                         LocationService locationService,
                                         ContainerInstanceQueryService containerInstanceQueryService,
                                         ContainerInstanceCommandService containerInstanceCommandService) {
        this.clientManager = clientManager;
        this.locationService = locationService;
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    public void removeExpiredInitializedAndCreationContainers() {
        List<ContainerInstanceView> initializedContainers = getByStatus(INITIALIZED, CREATION);

        initializedContainers.stream()
                .filter(initTimeExpiredValidator::validate)
                .forEach(view -> removeContainerInstance(view, INITIALIZATION_FAILED));
    }

    public void removeExpiredCreatedContainers() {
        List<ContainerInstanceView> createdContainers = getByStatus(CREATED);

        createdContainers.stream()
                .filter(createdTimeExpiredValidator::validate)
                .filter(this::doesNotExistsOnHost)
                .forEach(view -> removeContainerInstance(view, NOT_AVAILABLE_ON_HOST));
    }

    public void stopNotRunningStartedContainers() {
        List<ContainerInstanceView> startedContainers = getByStatus(STARTED);

        startedContainers.stream()
                .filter(startedTimeExpiredValidator::validate)
                .filter(this::isNotRunningOnHost)
                .forEach(view -> stopContainerInstance(view, NOT_RUNNING_ON_HOST));
    }

    public void removeExpiredStoppedContainers() {
        List<ContainerInstanceView> stoppedContainers = getByStatus(STOPPED);

        stoppedContainers.stream()
                .filter(stoppedTimeExpiredValidator::validate)
                .forEach(view -> removeContainerInstance(view, STOPPED_WAIT_TIME_EXCEEDED));
    }

    private void removeContainerInstance(ContainerInstanceView view, ContainerInstanceRemoveReason reason) {
        logger.info("Removing: {} with reason: {}", view.getContainerInstanceId(), reason);
        containerInstanceCommandService.remove(view.getContainerInstanceId(), reason);
    }

    private void stopContainerInstance(ContainerInstanceView view, ContainerInstanceStopReason reason) {
        logger.info("Stopping: {} with reason: {}", view.getContainerInstanceId(), reason);
        containerInstanceCommandService.stop(view.getContainerInstanceId(), reason);
    }

    private List<ContainerInstanceView> getByStatus(ContainerInstanceStatus ... statuses) {
        return Arrays.stream(statuses)
                .map(containerInstanceQueryService::getContainerInstanceViewsByStatus)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private boolean doesNotExistsOnHost(ContainerInstanceView view) {
        return existsOnHostValidator().invalidate(view);
    }

    private boolean isNotRunningOnHost(ContainerInstanceView view) {
        return isRunningOnHostValidator().invalidate(view);
    }

    private Validator<ContainerInstanceView> existsOnHostValidator() {
        return new BasicValidator<>(view -> {
            Server server = getServerByName(view.getServer().getName());
            DeployClient client = clientManager.createClient(server);
            return validateResult(client.containerExists(view.getContainerId()));
        });
    }

    private Validator<ContainerInstanceView> isRunningOnHostValidator() {
        return new BasicValidator<>(view -> {
            Server server = getServerByName(view.getServer().getName());
            DeployClient client = clientManager.createClient(server);
            return validateResult(client.isContainerRunning(view.getContainerId()));
        });
    }

    private boolean validateResult(DeployResult<Boolean> result) {
        return result.success() && result.get();
    }

    private Server getServerByName(String name) {
        return locationService.getServerByName(name);
    }
}
