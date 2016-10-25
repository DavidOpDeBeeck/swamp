package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.container.ProjectCreateConverter;
import de.daxu.swamp.api.dto.container.ProjectCreateDTO;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.common.response.Meta;
import de.daxu.swamp.common.response.Response;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.scheduler.manager.SchedulingManager;
import de.daxu.swamp.scheduler.strategy.FairStrategy;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.resource.project.ProjectResource.PROJECTS_URL;
import static de.daxu.swamp.common.response.Response.Builder.aResponse;

@RestController
@RequestMapping( PROJECTS_URL )
public class ProjectResource {

    public static final String PROJECTS_URL = "/projects";

    @Autowired
    SchedulingManager schedulingManager;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectConverter projectConverter;

    @Autowired
    ProjectCreateConverter projectCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Response> getAll() {

        List<ProjectDTO> projects = projectService.getAllProjects()
                .stream()
                .map( projectConverter::toDTO )
                .collect( Collectors.toList() );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( projects )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<Response> post( @RequestBody ProjectCreateDTO projectCreateDTO ) {

        Project project = projectCreateConverter.toDomain( projectCreateDTO );
        project = projectService.createProject( project );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( project )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<Response> get( @PathVariable( "projectId" ) String projectId ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        ProjectDTO projectDTO = projectConverter.toDTO( project );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( projectDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public ResponseEntity<Response> put( @PathVariable( "projectId" ) String projectId,
                                         @RequestBody ProjectCreateDTO projectCreateDTO ) {

        Project targetProject = projectService.getProject( projectId );
        Project srcProject = projectCreateConverter.toDomain( projectCreateDTO );

        if ( targetProject == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        BeanUtils.copyProperties( srcProject, targetProject );
        projectService.updateProject( targetProject );

        ProjectDTO projectDTO = projectConverter.toDTO( targetProject );

        Response response = aResponse()
                .withMeta( Meta.success() )
                .withData( projectDTO )
                .build();

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.DELETE )
    public ResponseEntity<Response> delete( @PathVariable( "projectId" ) String projectId ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        projectService.deleteProject( project );

        return new ResponseEntity<>( Response.success(), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", params = { "action=deploy" }, method = RequestMethod.POST )
    public ResponseEntity<Response> deploy( @PathVariable( "projectId" ) String projectId ) {

        Project project = projectService.getProject( projectId );

        if ( project == null )
            return new ResponseEntity<>( Response.notFound( "Project was not found!" ), HttpStatus.OK );

        schedulingManager.schedule( project, new FairStrategy() );

        return new ResponseEntity<>( Response.success(), HttpStatus.OK );
    }
}