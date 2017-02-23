package de.daxu.swamp.scheduling.batch.containerinstance;

import de.daxu.swamp.common.validator.BasicValidator;
import de.daxu.swamp.common.validator.Validator;
import de.daxu.swamp.common.validator.WaitTimeExpiredValidator;
import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
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

import java.util.List;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason.NOT_RUNNING_ON_HOST;

@Component
public class ContainerInstanceBatchService {

    private static final int DEFAULT_WAIT_TIME = 20;
    private static final int INIT_FAILED_WAIT_TIME = 20;
    private static final int STARTED_FAILED_WAIT_TIME = 20;
    private static final int STOPPED_FAILED_WAIT_TIME = 10;

    private final Logger logger = LoggerFactory.getLogger(ContainerInstanceBatchService.class);

    private final DeployFacade deployFacade;
    private final LocationService locationService;
    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    private final Validator<ContainerInstanceView> createdTimeValidator
            = new WaitTimeExpiredValidator<>(DEFAULT_WAIT_TIME, ContainerInstanceView::getCreatedAt);

    private final Validator<ContainerInstanceView> initializedTimeValidator
            = new WaitTimeExpiredValidator<>(INIT_FAILED_WAIT_TIME, ContainerInstanceView::getInitializedAt);

    private final Validator<ContainerInstanceView> startedTimeValidator
            = new WaitTimeExpiredValidator<>(STARTED_FAILED_WAIT_TIME, ContainerInstanceView::getStartedAt);

    private final Validator<ContainerInstanceView> stoppedTimeValidator
            = new WaitTimeExpiredValidator<>(STOPPED_FAILED_WAIT_TIME, ContainerInstanceView::getStoppedAt);

    @Autowired
    public ContainerInstanceBatchService(DeployFacade deployFacade,
                        LocationService locationService,
                        ContainerInstanceQueryService containerInstanceQueryService,
                        ContainerInstanceCommandService containerInstanceCommandService) {
        this.deployFacade = deployFacade;
        this.locationService = locationService;
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    public void removeExpiredInitializedContainers() {
        List<ContainerInstanceView> initializedContainers = getByStatus(INITIALIZED);

        initializedContainers.stream()
                .filter(initializedTimeValidator::validate)
                .forEach(view -> removeContainerInstance(view, INITIALIZATION_FAILED));
    }

    public void removeExpiredCreatedContainers() {
        List<ContainerInstanceView> createdContainers = getByStatus(CREATED);

        createdContainers.stream()
                .filter(createdTimeValidator::validate)
                .filter(this::doesNotExistsOnHost)
                .forEach(view -> removeContainerInstance(view, NOT_AVAILABLE_ON_HOST));
    }

    public void stopNotRunningStartedContainers() {
        List<ContainerInstanceView> startedContainers = getByStatus(STARTED);

        startedContainers.stream()
                .filter(startedTimeValidator::validate)
                .filter(this::isNotRunningOnHost)
                .forEach(view -> stopContainerInstance(view, NOT_RUNNING_ON_HOST));
    }

    public void removeExpiredStoppedContainers() {
        List<ContainerInstanceView> stoppedContainers = getByStatus(STOPPED);

        stoppedContainers.stream()
                .filter(stoppedTimeValidator::validate)
                .forEach(view -> removeContainerInstance(view, STOPPED_WAIT_TIME_EXCEEDED));
    }

    private void removeContainerInstance(ContainerInstanceView view, ContainerInstanceRemoveReason reason) {
        logger.info("Stopping: {} with reason: {}", view.getContainerInstanceId(), reason);
        containerInstanceCommandService.remove(view.getContainerInstanceId(), reason);
    }

    private void stopContainerInstance(ContainerInstanceView view, ContainerInstanceStopReason reason) {
        logger.info("Removing: {} with reason: {}", view.getContainerInstanceId(), reason);
        containerInstanceCommandService.stop(view.getContainerInstanceId(), reason);
    }

    private List<ContainerInstanceView> getByStatus(ContainerInstanceStatus status) {
        return containerInstanceQueryService.getContainerInstanceViewsByStatus(status);
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
            return deployFacade.containerClient(server).exists(view.getContainerId());
        });
    }

    private Validator<ContainerInstanceView> isRunningOnHostValidator() {
        return new BasicValidator<>(view -> {
            Server server = getServerByName(view.getServer().getName());
            return deployFacade.containerClient(server).isRunning(view.getContainerId());
        });
    }

    private Server getServerByName(String name) {
        return locationService.getServerByName(name);
    }
}
