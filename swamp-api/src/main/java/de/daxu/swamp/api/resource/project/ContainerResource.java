package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.core.strategy.SimpleStrategy;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.project.ContainerResource.CONTAINERS_URL;
import static de.daxu.swamp.api.resource.project.ProjectResource.PROJECTS_URL;

@RestController
@RequestMapping( CONTAINERS_URL )
public class ContainerResource {

    static final String CONTAINERS_URL = PROJECTS_URL + "/{projectId}/containers";

    private final ResponseFactory response;
    private final ProjectService projectService;
    private final ContainerConverter containerConverter;
    private final ContainerCreateConverter containerCreateConverter;
    private final ContainerInstanceWriteService containerInstanceWriteService;

    @Autowired
    public ContainerResource( ResponseFactory responseFactory,
                              ProjectService projectService,
                              ContainerConverter containerConverter,
                              ContainerCreateConverter containerCreateConverter,
                              ContainerInstanceWriteService containerInstanceWriteService ) {
        this.response = responseFactory;
        this.projectService = projectService;
        this.containerConverter = containerConverter;
        this.containerCreateConverter = containerCreateConverter;
        this.containerInstanceWriteService = containerInstanceWriteService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getContainers( @PathVariable( "projectId" ) String id ) {

        Project project = projectService.getProject( id );

        if( project == null )
            return response.notFound( "Project was not found!" );

        List<ContainerDTO> containers = project.getContainers()
                .stream()
                .map( containerConverter::toDTO )
                .collect( Collectors.toList() );

        return response.success( containers );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response postContainer( @PathVariable( "projectId" ) String id,
                                   @RequestBody ContainerCreateDTO containerCreateDTO ) {

        Project project = projectService.getProject( id );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = containerCreateConverter.toDomain( containerCreateDTO );
        container = projectService.addContainerToProject( project, container );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( container.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.GET )
    public Response getContainer( @PathVariable( "projectId" ) String projectId,
                                  @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = projectService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        ContainerDTO containerDTO = containerConverter.toDTO( container );

        return response.success( containerDTO );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.PUT )
    public Response putContainer( @PathVariable( "projectId" ) String projectId,
                                  @PathVariable( "containerId" ) String containerId,
                                  @RequestBody ContainerCreateDTO dto ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container targetContainer = projectService.getContainer( containerId );

        if( !project.getContainers().contains( targetContainer ) )
            return response.notFound( "Container was not found!" );

        Container srcContainer = containerCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( srcContainer, targetContainer );
        projectService.updateContainer( targetContainer );

        ContainerDTO containerDTO = containerConverter.toDTO( targetContainer );

        return response.success( containerDTO );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.DELETE )
    public Response deleteContainer( @PathVariable( "projectId" ) String projectId,
                                     @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = projectService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        projectService.removeContainerFromProject( project, container );

        return response.success();
    }

    @RequestMapping( value = "/{containerId}", params = { "action=schedule" }, method = RequestMethod.POST )
    public Response schedule( @PathVariable( "projectId" ) String projectId,
                              @PathVariable( value = "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = projectService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        Optional<Server> potentialServer = new SimpleStrategy().calculate( container );

        if( potentialServer.isPresent() ) {
            containerInstanceWriteService.schedule( container, potentialServer.get() );
            return response.success();
        }
        return response.badRequest( "Container has no available servers!" );
    }
}
