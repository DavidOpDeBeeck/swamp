package de.daxu.swamp.api.resource.scheduler;

import de.daxu.swamp.api.converter.scheduler.ContainerInstanceConverter;
import de.daxu.swamp.api.converter.scheduler.ProjectInstanceConverter;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.api.dto.scheduler.ProjectInstanceDTO;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.scheduler.ContainerInstance;
import de.daxu.swamp.scheduler.ProjectInstance;
import de.daxu.swamp.scheduler.command.CommandFactory;
import de.daxu.swamp.scheduler.manager.SchedulingManager;
import de.daxu.swamp.scheduler.service.SchedulingService;
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
public class SchedulerResource {

    @Autowired
    SchedulingManager schedulingManager;

    @Autowired
    SchedulingService schedulingService;

    @Autowired
    CommandFactory commandFactory;

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerInstanceConverter containerInstanceConverter;

    @Autowired
    ProjectInstanceConverter projectInstanceConverter;

    @RequestMapping( value = "/projects", method = RequestMethod.GET )
    public ResponseEntity<Collection<ProjectInstanceDTO>> getProjects() {
        Collection<ProjectInstanceDTO> projectInstances = schedulingService.getAllProjectInstances()
                .stream()
                .map( projectInstanceConverter::toDTO )
                .collect( Collectors.toList() );

        return new ResponseEntity<>( projectInstances, HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}", method = RequestMethod.GET )
    public ResponseEntity<ProjectInstanceDTO> getProject( @PathVariable( "projectId" ) String projectId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        ProjectInstanceDTO projectInstanceDTO = projectInstanceConverter.toDTO( projectInstance );

        return new ResponseEntity<>( projectInstanceDTO, HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerInstanceDTO>> getContainers( @PathVariable( "projectId" ) String projectId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        Collection<ContainerInstanceDTO> containerInstances = projectInstance.getContainerInstances()
                .stream()
                .map( containerInstanceConverter::toDTO )
                .collect( Collectors.toList() );

        return new ResponseEntity<>( containerInstances, HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", method = RequestMethod.GET )
    public ResponseEntity<ContainerInstanceDTO> getContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        ContainerInstance containerInstance = projectInstance.getContainerInstance( containerId );

        if ( containerInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        return new ResponseEntity<>( containerInstanceConverter.toDTO( containerInstance ), HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=start" }, method = RequestMethod.POST )
    public ResponseEntity startContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        ContainerInstance containerInstance = projectInstance.getContainerInstance( containerId );

        if ( containerInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        schedulingManager.schedule( containerInstance, commandFactory.startCommand() );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=stop" }, method = RequestMethod.POST )
    public ResponseEntity stopContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        ContainerInstance containerInstance = projectInstance.getContainerInstance( containerId );

        if ( containerInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        schedulingManager.schedule( containerInstance, commandFactory.stopCommand() );

        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/projects/{projectId}/containers/{containerId}", params = { "action=restart" }, method = RequestMethod.POST )
    public ResponseEntity restartContainer( @PathVariable( "projectId" ) String projectId, @PathVariable( "containerId" ) String containerId ) {
        ProjectInstance projectInstance = schedulingService.getProjectInstance( projectId );

        if ( projectInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        ContainerInstance containerInstance = projectInstance.getContainerInstance( containerId );

        if ( containerInstance == null )
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );

        schedulingManager.schedule( containerInstance, commandFactory.restartCommand() );

        return new ResponseEntity<>( HttpStatus.OK );
    }
}
