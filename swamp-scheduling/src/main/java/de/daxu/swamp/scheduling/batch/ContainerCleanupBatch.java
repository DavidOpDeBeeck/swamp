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

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;
import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;

@Component
public class ContainerCleanupBatch {

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

    @Scheduled( fixedDelay = 5000 )
    public void cleanupContainers() {
        Set<ContainerInstanceView> runningContainers = getRunningContainers();

        logger.info( "{} running containers", runningContainers.size() );

        runningContainers.stream()
                .filter( this::getInCompleteContainers )
                .forEach( view -> containerInstanceCommandService.remove( view.getContainerInstanceId() ) );

        runningContainers.stream()
                .filter( this::getCompleteContainers )
                .forEach( this::removeMissingAndNotRunningContainers );
    }

    private void removeMissingAndNotRunningContainers( ContainerInstanceView view ) {
        ServerView server = view.getServer();
        ContainerId containerId = view.getContainerId();

        boolean exists = containerClient( server ).exists( containerId );

        if( !exists ) {
            removeContainer( view.getContainerInstanceId() );
            return;
        }

        boolean running = containerClient( server ).isRunning( containerId );

        if( !running ) {
            stopContainer( view.getContainerInstanceId() );
        }
    }

    private void removeContainer( ContainerInstanceId id ) {
        containerInstanceCommandService.remove( id );

        logger.info( "\n**************************\n"
                + "Removing : " + id
                + "\n**************************\n" );
    }

    private void stopContainer( ContainerInstanceId id ) {
        containerInstanceCommandService.stop( id );

        logger.info( "\n**************************\n"
                + "Stopping : " + id
                + "\n**************************\n" );
    }

    private ContainerClient containerClient( ServerView server ) {
        return deployFacade.containerClient( getServerByName( server.getName() ) );
    }

    private boolean getInCompleteContainers( ContainerInstanceView view ) {
        return !getCompleteContainers( view );
    }

    private boolean getCompleteContainers( ContainerInstanceView view ) {
        return view.getContainerId() != null && isServerComplete( view.getServer() );
    }

    private boolean isServerComplete( ServerView serverView ) {
        return serverView != null && !serverView.getName().isEmpty();
    }

    private Set<ContainerInstanceView> getRunningContainers() {
        Set<ContainerInstanceView> views = newHashSet();
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( INITIALIZED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( CREATED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( STARTED ) );
        return views;
    }

    private Server getServerByName( String name ) {
        return locationService.getServerByName( name );
    }
}
