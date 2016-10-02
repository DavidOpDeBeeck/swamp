package de.daxu.swamp.api.endpoint;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.container.ProjectCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.api.dto.container.ProjectCreateDTO;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/projects" )
public class ProjectEndpoint {

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

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<ProjectDTO> get( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( projectConverter.toDTO( projectService.getProject( id ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity delete( @PathVariable( "id" ) String id ) {
        Project project = projectService.getProject( id );
        projectService.deleteProject( project );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/containers", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerDTO>> getContainers( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( projectService.getProject( id ).getContainers()
                .stream()
                .map( containerConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/containers", method = RequestMethod.POST )
    public ResponseEntity<ContainerDTO> postContainer( @PathVariable( "id" ) String id, @RequestBody ContainerCreateDTO dto ) {
        Project project = projectService.getProject( id );
        Container container = projectService.addContainerToProject( project, containerCreateConverter.toDomain( dto ) );
        return new ResponseEntity<>( containerConverter.toDTO( container ), HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}/containers/{containerId}", method = RequestMethod.DELETE )
    public ResponseEntity deleteContainer( @PathVariable( "id" ) String id, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( id );
        Container container = projectService.getContainer( containerId );
        projectService.removeContainerFromProject( project, container );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    /*
        @Autowired
        Scheduler scheduler;

        @RequestMapping( value = "/{id}", method = RequestMethod.POST )
        public ResponseEntity run( @PathVariable( "id" ) String id ) {
            Collection<Container> containers = projectService.getContainers( id );
            containers.stream().forEach( scheduler::schedule );
            return new ResponseEntity<>( HttpStatus.OK );
        }
    */
}
