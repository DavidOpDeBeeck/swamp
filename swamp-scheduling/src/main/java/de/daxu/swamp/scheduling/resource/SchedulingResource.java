package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.scheduling.read.ContainerInstanceReadService;
import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;
import static de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus.*;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    final static String SCHEDULING_URL = "/scheduling/containerInstances";

    private final ContainerInstanceReadService containerInstanceReadService;
    private final ResponseFactory response;

    @Autowired
    public SchedulingResource( ContainerInstanceReadService containerInstanceReadService,
                               ResponseFactory responseFactory ) {
        this.containerInstanceReadService = containerInstanceReadService;
        this.response = responseFactory;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll() {

        List<ContainerInstanceView> views = new ArrayList<>();
        views.addAll( containerInstanceReadService.getContainerInstanceViewsByStatus( INITIALIZED ) );
        views.addAll( containerInstanceReadService.getContainerInstanceViewsByStatus( STARTED ) );
        views.addAll( containerInstanceReadService.getContainerInstanceViewsByStatus( CREATED ) );

        return response.success( views );
    }

    @RequestMapping( value = "/{containerInstanceId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "containerInstanceId" ) String containerInstanceId ) {

        ContainerInstanceView view = containerInstanceReadService
                .getContainerInstanceViewById( ContainerInstanceId.from( containerInstanceId ) );

        if( view == null )
            return response.notFound( "Container instance was not found!" );

        return response.success( view );
    }
}
