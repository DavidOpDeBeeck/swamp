package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.common.response.Meta;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.project.ContainerResource.CONTAINERS_URL;
import static de.daxu.swamp.api.resource.project.ProjectResource.PROJECTS_URL;
import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@RestController
@RequestMapping( CONTAINERS_URL )
public class ContainerResource {

    public static final String CONTAINERS_URL = PROJECTS_URL + "/{projectId}/containers";

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerConverter containerConverter;

    @Autowired
    ContainerCreateConverter containerCreateConverter;

    @RequestMapping( value = "/{projectId}/containers", method = RequestMethod.GET )
    public ResponseEntity<Response> getContainers( @PathVariable( "projectId" ) String id ) {

        Project project = projectService.getProject( id );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        List<ContainerDTO> containers = project.getContainers()
                .stream()
                .map( containerConverter::toDTO )
                .collect( Collectors.toList() );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( containers )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/containers", method = RequestMethod.POST )
    public ResponseEntity<Response> postContainer( @PathVariable( "projectId" ) String id,
                                                   @RequestBody ContainerCreateDTO containerCreateDTO ) {

        Project project = projectService.getProject( id );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        Container container = containerCreateConverter.toDomain( containerCreateDTO );
        container = projectService.addContainerToProject( project, container );
        ContainerDTO containerDTO = containerConverter.toDTO( container );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( containerDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.GET )
    public ResponseEntity<Response> getContainer( @PathVariable( "projectId" ) String projectId,
                                                  @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        Container container = projectService.getContainer( containerId );

        if ( !project.getContainers().contains( container ) )
            return new ResponseEntity<>( Response.badRequest( "Project doesn't have the container!" ), HttpStatus.OK );

        ContainerDTO containerDTO = containerConverter.toDTO( container );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( containerDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{containerId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> putContainer( @PathVariable( "projectId" ) String projectId,
                                                  @PathVariable( "containerId" ) String containerId,
                                                  @RequestBody ContainerCreateDTO dto ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        Container targetContainer = projectService.getContainer( containerId );

        if ( !project.getContainers().contains( targetContainer ) )
            return new ResponseEntity<>( Response.badRequest( "Project doesn't have the container!" ), HttpStatus.OK );

        Container srcContainer = containerCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( srcContainer, targetContainer );
        projectService.updateContainer( targetContainer );

        ContainerDTO containerDTO = containerConverter.toDTO( targetContainer );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( containerDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity<Response> deleteContainer( @PathVariable( "projectId" ) String projectId,
                                           @PathVariable( "containerId" ) String containerId ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        Container container = projectService.getContainer( containerId );

        if ( !project.getContainers().contains( container ) )
            return new ResponseEntity<>( Response.badRequest( "Project doesn't have the container!" ), HttpStatus.OK );

        projectService.removeContainerFromProject( project, container );

        return new ResponseEntity<>( Response.success(), HttpStatus.OK );
    }
}
