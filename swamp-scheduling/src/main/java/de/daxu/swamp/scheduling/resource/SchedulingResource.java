package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.scheduling.read.ContainerInstanceReadService;
import de.daxu.swamp.scheduling.read.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    public final static String SCHEDULING_URL = "/scheduling/containerInstances";

    private final ContainerInstanceWriteService containerInstanceWriteService;
    private final ContainerInstanceReadService containerInstanceReadService;
    private final ResponseFactory responseFactory;

    @Autowired
    public SchedulingResource( ContainerInstanceWriteService containerInstanceWriteService,
                               ContainerInstanceReadService containerInstanceReadService,
                               ResponseFactory responseFactory ) {
        this.containerInstanceWriteService = containerInstanceWriteService;
        this.containerInstanceReadService = containerInstanceReadService;
        this.responseFactory = responseFactory;
    }

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getAll() {

        List<ContainerInstanceView> views = new ArrayList<>();
        views.addAll( containerInstanceReadService.getContainerInstanceViewsByStatus( ContainerInstanceStatus.STARTED ) );
        views.addAll( containerInstanceReadService.getContainerInstanceViewsByStatus( ContainerInstanceStatus.CREATED ) );

        return new ResponseEntity<>( responseFactory.success( views ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{containerInstanceId}", method = RequestMethod.GET )
    public ResponseEntity<Response> get( @PathVariable( "containerInstanceId" ) String containerInstanceId ) {

        ContainerInstanceView view = containerInstanceReadService
                .getContainerInstanceViewById( ContainerInstanceId.from( containerInstanceId ) );

        if( view == null )
            return new ResponseEntity<>( responseFactory.notFound( "Container instance was not found!" ), HttpStatus.OK );

        return new ResponseEntity<>( responseFactory.success( view ), HttpStatus.OK );
    }
}
