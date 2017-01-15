package de.daxu.swamp.scheduling.batch;

import de.daxu.swamp.core.location.LocationService;
import de.daxu.swamp.core.server.Server;
import de.daxu.swamp.deploy.DeployFacade;
import de.daxu.swamp.deploy.client.ContainerClient;
import de.daxu.swamp.deploy.container.ContainerId;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceCommandService;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.query.containerinstance.ServerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceRemoveReason.*;
import static de.daxu.swamp.scheduling.command.containerinstance.reason.ContainerInstanceStopReason.NOT_RUNNING_ON_HOST;

@Component
public class ContainerCleanupBatch {

    private static final int CONTAINER_DEPLOY_FAILED_WAIT_TIME_SECONDS = 20;

    private final Logger logger = LoggerFactory.getLogger( ContainerCleanupBatch.class );

    private final DeployFacade deployFacade;
    private final LocationService locationService;
    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ContainerInstanceCommandService containerInstanceCommandService;

    @Autowired
    public ContainerCleanupBatch( DeployFacade deployFacade,
                                  LocationService locationService,
                                  ContainerInstanceQueryService containerInstanceQueryService,
                                  ContainerInstanceCommandService containerInstanceCommandService ) {
        this.deployFacade = deployFacade;
        this.locationService = locationService;
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.containerInstanceCommandService = containerInstanceCommandService;
    }

    @Scheduled( fixedDelay = 10000 )
    public void cleanupContainers() {
        Set<ContainerInstanceView> runningContainers = getRunningContainers();

        logger.info( "{} running containers", runningContainers.size() );

        runningContainers.stream()
                .filter( this::deployFailed )
                .forEach( this::removeContainerWithDeployWaitTimeExceededReason );

        runningContainers.stream()
                .filter( this::serverConfigMissing )
                .forEach( this::removeContainerWithInvalidConfigReason );

        runningContainers.stream()
                .filter( this::getDeployedContainers )
                .forEach( this::removeMissingAndNotRunningContainers );
    }

    private boolean getDeployedContainers( ContainerInstanceView view ) {
        return !deployFailed( view ) && !serverConfigMissing( view );
    }

    private boolean deployFailed( ContainerInstanceView view ) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime initializedAt = view.getInitializedAt();
        LocalDateTime initializedPlusWaitTime = initializedAt.plusSeconds( CONTAINER_DEPLOY_FAILED_WAIT_TIME_SECONDS );
        return view.getContainerId() == null && now.isAfter( initializedPlusWaitTime );
    }

    private boolean serverConfigMissing( ContainerInstanceView view ) {
        ServerView server = view.getServer();
        return server == null || server.getIp().isEmpty();
    }

    private Set<ContainerInstanceView> getRunningContainers() {
        Set<ContainerInstanceView> views = newHashSet();
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( INITIALIZED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( CREATED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( STARTED ) );
        return views;
    }

    private void removeMissingAndNotRunningContainers( ContainerInstanceView view ) {
        ServerView server = view.getServer();
        ContainerId containerId = view.getContainerId();

        boolean exists = containerClient( server ).exists( containerId );

        if( !exists ) {
            removeContainerWithNotAvailableOnHostReason( view.getContainerInstanceId() );
            return;
        }

        boolean running = containerClient( server ).isRunning( containerId );

        if( !running ) {
            stopContainer( view.getContainerInstanceId() );
        }
    }

    private void removeContainerWithDeployWaitTimeExceededReason( ContainerInstanceView view ) {
        containerInstanceCommandService.remove( view.getContainerInstanceId(), DEPLOY_WAIT_TIME_EXCEEDED );
        logger.info( "Removing : {}, reason: DEPLOY_WAIT_TIME_EXCEEDED", view.getContainerInstanceId() );
    }

    private void removeContainerWithInvalidConfigReason( ContainerInstanceView view ) {
        containerInstanceCommandService.remove( view.getContainerInstanceId(), INVALID_CONTAINER_CONFIG );
        logger.info( "Removing : {}, reason: INVALID_CONTAINER_CONFIG", view.getContainerInstanceId() );
    }

    private void removeContainerWithNotAvailableOnHostReason( ContainerInstanceId id ) {
        containerInstanceCommandService.remove( id, NOT_AVAILABLE_ON_HOST );
        logger.info( "Removing : {}, reason: NOT_AVAILABLE_ON_HOST", id );
    }

    private void stopContainer( ContainerInstanceId id ) {
        containerInstanceCommandService.stop( id, NOT_RUNNING_ON_HOST );
        logger.info( "Stopping : {}, reason: NOT_RUNNING_ON_HOST", id );
    }

    private ContainerClient containerClient( ServerView server ) {
        return deployFacade.containerClient( getServerByName( server.getName() ) );
    }

    private Server getServerByName( String name ) {
        return locationService.getServerByName( name );
    }
}
