package de.daxu.swamp.scheduling.resource.containerinstance;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceQueryService;
import de.daxu.swamp.scheduling.query.containerinstance.ContainerInstanceView;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.resource.containerinstance.ContainerInstanceResource.CONTAINER_INSTANCE_URL;
import static de.daxu.swamp.scheduling.resource.projectinstance.ProjectInstanceResource.PROJECT_INSTANCE_URL;

@RestController
@RequestMapping( CONTAINER_INSTANCE_URL )
public class ContainerInstanceResource {

    final static String CONTAINER_INSTANCE_URL = PROJECT_INSTANCE_URL + "/{projectInstanceId}/containerInstances";

    private final ContainerInstanceQueryService containerInstanceQueryService;
    private final ResponseFactory response;

    @Autowired
    public ContainerInstanceResource( ContainerInstanceQueryService containerInstanceQueryService,
                                      ResponseFactory responseFactory ) {
        this.containerInstanceQueryService = containerInstanceQueryService;
        this.response = responseFactory;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getContainerInstances( @PathVariable( "projectInstanceId" ) ProjectInstanceView view ) {

        Set<ContainerInstanceView> containers = view.getContainers()
                .stream()
                .map( containerInstanceQueryService::getContainerInstanceViewById )
                .collect( Collectors.toSet() );

        return response.success( containers );
    }

    @RequestMapping( value = "/{containerInstanceId}", method = RequestMethod.GET )
    public Response getContainerInstance( @PathVariable( "containerInstanceId" ) ContainerInstanceView view ) {

        return response.success( view );
    }
}
