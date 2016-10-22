package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.container.ProjectCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.api.dto.container.ProjectCreateDTO;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.SchedulerImpl;
import de.daxu.swamp.core.scheduler.manager.SchedulingManager;
import de.daxu.swamp.core.scheduler.strategy.FairStrategy;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/projects" )
public class ProjectResource {

    @Autowired
    SchedulingManager schedulingManager;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectConverter projectConverter;

    @Autowired
    ProjectCreateConverter projectCreateConverter;

    @Autowired
    ContainerConverter containerConverter;

    @Autowired
    ContainerCreateConverter containerCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<Collection<ProjectDTO>> getAll() {
        return new ResponseEntity<>( projectService.getAllProjects()
                .stream()
                .map( projectConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.POST )
    public ResponseEntity<ProjectDTO> post( @RequestBody ProjectCreateDTO dto ) {
        Project project = projectService.createProject( projectCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( projectConverter.toDTO( project ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<ProjectDTO> get( @PathVariable( "projectId" ) String id ) {
        return new ResponseEntity<>( projectConverter.toDTO( projectService.getProject( id ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public ResponseEntity<ProjectDTO> put( @PathVariable( "projectId" ) String id,
                                                  @RequestBody ProjectCreateDTO dto ) {
        Project oldProject = projectService.getProject( id );
        Project newProject = projectCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( newProject, oldProject );
        projectService.updateProject( oldProject );

        return new ResponseEntity<>( projectConverter.toDTO( oldProject ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "projectId" ) String id ) {
        Project project = projectService.getProject( id );
        projectService.deleteProject( project );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}", params = { "action=deploy" }, method = RequestMethod.POST )
    public ResponseEntity deploy( @PathVariable( "projectId" ) String id ) {
        Project project = projectService.getProject( id );
        schedulingManager.schedule( project, new FairStrategy() );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/containers", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerDTO>> getContainers( @PathVariable( "projectId" ) String id ) {
        return new ResponseEntity<>( projectService.getProject( id ).getContainers()
                .stream()
                .map( containerConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{projectId}/containers", method = RequestMethod.POST )
    public ResponseEntity<ContainerDTO> postContainer( @PathVariable( "projectId" ) String id, @RequestBody ContainerCreateDTO dto ) {
        Project project = projectService.getProject( id );
        Container container = projectService.addContainerToProject( project, containerCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( containerConverter.toDTO( container ), HttpStatus.OK );
    }
}
