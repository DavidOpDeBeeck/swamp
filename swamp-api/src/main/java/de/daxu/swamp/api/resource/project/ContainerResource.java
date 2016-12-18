package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.response.ResponseFactory;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.ContainerService;
import de.daxu.swamp.core.location.server.Server;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectService;
import de.daxu.swamp.core.strategy.FirstInLineStrategy;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
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
    private final ContainerService containerService;
    private final ContainerConverter containerConverter;
    private final ContainerCreateConverter containerCreateConverter;
    private final ContainerInstanceWriteService containerInstanceWriteService;

    @Autowired
    public ContainerResource( ResponseFactory responseFactory,
                              ProjectService projectService,
                              ContainerService containerService,
                              ContainerConverter containerConverter,
                              ContainerCreateConverter containerCreateConverter,
                              ContainerInstanceWriteService containerInstanceWriteService ) {
        this.response = responseFactory;
        this.projectService = projectService;
        this.containerService = containerService;
        this.containerConverter = containerConverter;
        this.containerCreateConverter = containerCreateConverter;
        this.containerInstanceWriteService = containerInstanceWriteService;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll( @PathVariable( "projectId" ) String id ) {

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
    public Response post( @PathVariable( "projectId" ) String id,
                                   @RequestBody ContainerCreateDTO containerCreateDTO ) {

        Project project = projectService.getProject( id );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = containerCreateConverter.toDomain( containerCreateDTO );
        container = containerService.addContainerToProject( project, container );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( container.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "projectId" ) String projectId,
                                  @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = containerService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        ContainerDTO containerDTO = containerConverter.toDTO( container );

        return response.success( containerDTO );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.PUT )
    public Response put( @PathVariable( "projectId" ) String projectId,
                                  @PathVariable( "containerId" ) String containerId,
                                  @RequestBody ContainerCreateDTO dto ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container targetContainer = containerService.getContainer( containerId );

        if( !project.getContainers().contains( targetContainer ) )
            return response.notFound( "Container was not found!" );

        Container srcContainer = containerCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( srcContainer, targetContainer );
        containerService.updateContainer( targetContainer );

        ContainerDTO containerDTO = containerConverter.toDTO( targetContainer );

        return response.success( containerDTO );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.DELETE )
    public Response delete( @PathVariable( "projectId" ) String projectId,
                                     @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = containerService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        containerService.removeContainerFromProject( project, container );

        return response.success();
    }

    @RequestMapping( value = "/{containerId}", params = { "action=schedule" }, method = RequestMethod.POST )
    public Response schedule( @PathVariable( "projectId" ) String projectId,
                              @PathVariable( value = "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if( project == null )
            return response.notFound( "Project was not found!" );

        Container container = containerService.getContainer( containerId );

        if( !project.getContainers().contains( container ) )
            return response.notFound( "Container was not found!" );

        Optional<Server> server = new FirstInLineStrategy().locate( container.getPotentialLocations() );

        if( server.isPresent() ) {
            containerInstanceWriteService.schedule( container, server.get() );
            return response.success();
        }
        return response.badRequest( "Container has no available servers!" );
    }
}
