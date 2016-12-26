package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.query.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static de.daxu.swamp.scheduling.command.containerinstance.ContainerInstanceStatus.*;
import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    final static String SCHEDULING_URL = "/scheduling/containerInstances";

    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ResponseFactory response;

    @Autowired
    public SchedulingResource( ContainerInstanceQueryService containerInstanceQueryService,
                               ResponseFactory responseFactory ) {
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.response = responseFactory;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll() {

        List<ContainerInstanceView> views = new ArrayList<>();
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( INITIALIZED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( STARTED ) );
        views.addAll( containerInstanceQueryService.getContainerInstanceViewsByStatus( CREATED ) );

        return response.success( views );
    }

    @RequestMapping( value = "/{containerInstanceId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "containerInstanceId" ) String containerInstanceId ) {

        ContainerInstanceView view = containerInstanceQueryService
                .getContainerInstanceViewById( ContainerInstanceId.from( containerInstanceId ) );

        if( view == null )
            return response.notFound( "Container instance was not found!" );

        return response.success( view );
    }
}
