package de.daxu.swamp.api.endpoint.scheduler;

import de.daxu.swamp.api.converter.scheduler.ContainerInstanceConverter;
import de.daxu.swamp.api.dto.scheduler.ContainerInstanceDTO;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.core.scheduler.Scheduler;
import de.daxu.swamp.core.scheduler.strategy.FairStrategy;
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
@RequestMapping( value = "/schedule" )
public class SchedulerEndpoint {

    @Autowired
    Scheduler scheduler;

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerInstanceConverter containerInstanceConverter;

    @RequestMapping( value = "/{id}", method = RequestMethod.POST )
    public ResponseEntity runProject( @PathVariable( "id" ) String id ) {
        Project project = projectService.getProject( id );
        scheduler.schedule( project, new FairStrategy() );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.GET )
    public ResponseEntity<Collection<ContainerInstanceDTO>> getProject( @PathVariable( "id" ) String id ) {
        Project project = projectService.getProject( id );
        return new ResponseEntity<>( scheduler.getInstances( project )
                .stream()
                .map( containerInstanceConverter::toDTO )
                .collect( Collectors.toList() ), HttpStatus.OK );
    }
}
