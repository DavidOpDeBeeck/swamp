package de.daxu.swamp.api.endpoint.scheduler;

import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.scheduler.ContainerInstanceConverter;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping( value = "/scheduler" )
public class SchedulerEndpoint {

    @Autowired
    Scheduler scheduler;

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerInstanceConverter containerInstanceConverter;

    @Autowired
    ProjectConverter projectConverter;

    @RequestMapping( value = "/projects", method = RequestMethod.GET )
    public ResponseEntity<Collection<ProjectDTO>> getProjects() {
        return new ResponseEntity<>( scheduler.getProjects()
                .stream()
                .map( projectConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{id}", method = RequestMethod.GET )
    public ResponseEntity<ProjectDTO> getProject( @PathVariable( "id" ) String id ) {
        return new ResponseEntity<>( projectConverter.toDTO( scheduler.getProjects().stream().filter( p -> p.getId().equals( id ) ).findFirst().orElse( null ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{id}/containers", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerInstanceDTO>> getContainers( @PathVariable( "id" ) String id ) {
        Project project = projectService.getProject( id );
        return new ResponseEntity<>( scheduler.getInstances( project )
                .stream()
                .map( containerInstanceConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{id}/containers/{containerId}", method = RequestMethod.GET )
    public ResponseEntity<ContainerInstanceDTO> getContainer( @PathVariable( "id" ) String id, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( id );
        ContainerInstance containerInstance = scheduler.getInstances( project )
                .stream()
                .filter( instance -> instance.getContainer().getId().equals( containerId ) )
                .findFirst().orElse( null );
        return new ResponseEntity<>( containerInstanceConverter.toDTO( containerInstance ), HttpStatus.OK );
    }
}
