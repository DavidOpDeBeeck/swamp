package de.daxu.swamp.api.resource.scheduler;

import de.daxu.swamp.api.converter.container.ProjectConverter;
import de.daxu.swamp.api.converter.scheduler.ContainerInstanceConverter;
import de.daxu.swamp.api.dto.container.ProjectDTO;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.ContainerInstance;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.manager.SchedulingManager;
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

import static de.daxu.swamp.core.scheduler.command.CommandFactory.restartCommand;
import static de.daxu.swamp.core.scheduler.command.CommandFactory.startCommand;
import static de.daxu.swamp.core.scheduler.command.CommandFactory.stopCommand;

@RestController
@RequestMapping( value = "/scheduler" )
public class SchedulerResource {

    @Autowired
    SchedulingManager schedulingManager;

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerInstanceConverter containerInstanceConverter;

    @Autowired
    ProjectConverter projectConverter;

    @RequestMapping( value = "/projects", method = RequestMethod.GET )
    public ResponseEntity<Collection<ProjectDTO>> getProjects() {
        return new ResponseEntity<>( schedulingManager.getProjects()
                .stream()
                .map( projectConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<ProjectDTO> getProject( @PathVariable( "projectId" ) String projectId ) {
        return new ResponseEntity<>( projectConverter.toDTO( schedulingManager.getProjects().stream()
                .filter( p -> p.getId().equals( projectId ) )
                .findFirst().orElse( null ) ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerInstanceDTO>> getContainers( @PathVariable( "projectId" ) String projectId ) {
        Project project = projectService.getProject( projectId );
        return new ResponseEntity<>( schedulingManager.getInstances( project )
                .stream()
                .map( containerInstanceConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", method = RequestMethod.GET )
    public ResponseEntity<ContainerInstanceDTO> getContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( projectId );
        ContainerInstance containerInstance = schedulingManager.getInstances( project )
                .stream()
                .filter( instance -> instance.getContainer().getId().equals( containerId ) )
                .findFirst().orElse( null );
        return new ResponseEntity<>( containerInstanceConverter.toDTO( containerInstance ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=start" }, method = RequestMethod.POST )
    public ResponseEntity startContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( projectId );
        ContainerInstance containerInstance = schedulingManager.getInstances( project )
                .stream()
                .filter( instance -> instance.getContainer().getId().equals( containerId ) )
                .findFirst().orElse( null );
        schedulingManager.schedule( containerInstance, startCommand() );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=stop" }, method = RequestMethod.POST )
    public ResponseEntity stopContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( projectId );
        ContainerInstance containerInstance = schedulingManager.getInstances( project )
                .stream()
                .filter( instance -> instance.getContainer().getId().equals( containerId ) )
                .findFirst().orElse( null );
        schedulingManager.schedule( containerInstance, stopCommand() );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=restart" }, method = RequestMethod.POST )
    public ResponseEntity restartContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( projectId );
        ContainerInstance containerInstance = schedulingManager.getInstances( project )
                .stream()
                .filter( instance -> instance.getContainer().getId().equals( containerId ) )
                .findFirst().orElse( null );
        schedulingManager.schedule( containerInstance, restartCommand() );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
