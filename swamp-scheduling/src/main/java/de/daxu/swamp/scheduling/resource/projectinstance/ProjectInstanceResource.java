package de.daxu.swamp.scheduling.resource.projectinstance;

import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceQueryService;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.scheduling.resource.projectinstance.ProjectInstanceResource.PROJECT_INSTANCE_URL;

@RestController
@RequestMapping( PROJECT_INSTANCE_URL )
public class ProjectInstanceResource {

    public final static String PROJECT_INSTANCE_URL = "/projectInstances";

    private final ProjectInstanceQueryService projectInstanceQueryService;
    private final ResponseFactory response;

    @Autowired
    public ProjectInstanceResource( ProjectInstanceQueryService projectInstanceQueryService,
                                    ResponseFactory responseFactory ) {
        this.projectInstanceQueryService = projectInstanceQueryService;
        this.response = responseFactory;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll() {

        return response.success( projectInstanceQueryService.getAllProjectInstancesView() );
    }

    @RequestMapping( value = "/{projectInstanceId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "projectInstanceId" ) ProjectInstanceView view ) {

        return response.success( view );
    }
}
