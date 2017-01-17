package de.daxu.swamp.scheduling.task;

import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason;
import de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.query.containerinstance.ServerView;
import de.daxu.swamp.scheduling.task.statuscheck.ExistsOnHostCheck;
import de.daxu.swamp.scheduling.task.statuscheck.RunningOnHostCheck;
import de.daxu.swamp.scheduling.task.statuscheck.WaitTimeCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason.NOT_RUNNING_ON_HOST;

@Component
public class CleanupBatch {

    private static final int INDIVIDUAL_TASK_DELAY = 15000;

    private static final int CONTAINER_INITIALIZATION_FAILED_WAIT_TIME_SECONDS = 20;
    private static final int CONTAINER_STOPPED_WAIT_TIME_SECONDS = 20;
    private static final int CONTAINER_DEFAULT_ACTION_WAIT_TIME_SECONDS = 20;

    private final Logger logger = LoggerFactory.getLogger( CleanupBatch.class );

    private final DeployFacade deployFacade;
    private final LocationService locationService;
    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    private final WaitTimeCheck initializedTimeCheck
            = new WaitTimeCheck( CONTAINER_INITIALIZATION_FAILED_WAIT_TIME_SECONDS, ContainerInstanceView::getInitializedAt );

    private final WaitTimeCheck stoppedTimeCheck
            = new WaitTimeCheck( CONTAINER_STOPPED_WAIT_TIME_SECONDS, ContainerInstanceView::getStoppedAt );

    @Autowired
    public CleanupBatch( DeployFacade deployFacade,
                         LocationService locationService,
                         ContainerInstanceQueryService containerInstanceQueryService,
                         ContainerInstanceCommandService containerInstanceCommandService ) {
        this.deployFacade = deployFacade;
        this.locationService = locationService;
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @Scheduled( fixedDelay = INDIVIDUAL_TASK_DELAY )
    public void cleanUpInitializedContainers() {
        List<ContainerInstanceView> initializedContainers = containerInstanceQueryService.getContainerInstanceViewsByStatus( INITIALIZED );

        initializedContainers.stream()
                .filter( initializedTimeCheck::check )
                .forEach( view -> removeContainerInstance( view, INITIALIZATION_FAILED ) );
    }

    @Scheduled( fixedDelay = INDIVIDUAL_TASK_DELAY )
    public void cleanCreatedContainers() {
        List<ContainerInstanceView> createdContainers = containerInstanceQueryService.getContainerInstanceViewsByStatus( CREATED );

        createdContainers.stream()
                .filter( defaultActionTimeCheck( ContainerInstanceView::getCreatedAt ) )
                .filter( doesNotExistsOnHost() )
                .forEach( view -> removeContainerInstance( view, NOT_AVAILABLE_ON_HOST ) );
    }

    @Scheduled( fixedDelay = INDIVIDUAL_TASK_DELAY )
    public void cleanStartedContainers() {
        List<ContainerInstanceView> startedContainers = containerInstanceQueryService.getContainerInstanceViewsByStatus( STARTED );

        startedContainers.stream()
                .filter( defaultActionTimeCheck( ContainerInstanceView::getStartedAt ) )
                .filter( isNotRunningOnHost() )
                .forEach( view -> stopContainerInstance( view, NOT_RUNNING_ON_HOST ) );
    }

    @Scheduled( fixedDelay = INDIVIDUAL_TASK_DELAY )
    public void cleanStoppedContainers() {
        List<ContainerInstanceView> stoppedContainers = containerInstanceQueryService.getContainerInstanceViewsByStatus( STOPPED );

        stoppedContainers.stream()
                .filter( stoppedTimeCheck::check )
                .forEach( view -> removeContainerInstance( view, STOPPED_WAIT_TIME_EXCEEDED ) );
    }

    private void stopContainerInstance( ContainerInstanceView view, ContainerInstanceStopReason reason ) {
        logger.info( "Removing: {} with reason: {}", view.getContainerInstanceId(), reason );
        containerInstanceCommandService.stop( view.getContainerInstanceId(), reason );
    }

    private void removeContainerInstance( ContainerInstanceView view, ContainerInstanceRemoveReason reason ) {
        logger.info( "Stopping: {} with reason: {}", view.getContainerInstanceId(), reason );
        containerInstanceCommandService.remove( view.getContainerInstanceId(), reason );
    }

    private Predicate<ContainerInstanceView> defaultActionTimeCheck( Function<ContainerInstanceView, LocalDateTime> dateTimeFunction ) {
        return view -> new WaitTimeCheck( CONTAINER_DEFAULT_ACTION_WAIT_TIME_SECONDS, dateTimeFunction ).check( view );
    }

    private Predicate<ContainerInstanceView> doesNotExistsOnHost() {
        return view -> !existsOnHostCheck( view.getServer() ).check( view );
    }

    private Predicate<ContainerInstanceView> isNotRunningOnHost() {
        return view -> !runningOnHostCheck( view.getServer() ).check( view );
    }

    private ExistsOnHostCheck existsOnHostCheck( ServerView serverView ) {
        return new ExistsOnHostCheck( getServerByName( serverView.getName() ), deployFacade );
    }

    private RunningOnHostCheck runningOnHostCheck( ServerView serverView ) {
        return new RunningOnHostCheck( getServerByName( serverView.getName() ), deployFacade );
    }

    private Server getServerByName( String name ) {
        return locationService.getServerByName( name );
    }
}
