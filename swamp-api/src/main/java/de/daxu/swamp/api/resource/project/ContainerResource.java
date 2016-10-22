package de.daxu.swamp.api.resource.project;

import de.daxu.swamp.api.converter.container.ContainerConverter;
import de.daxu.swamp.api.converter.container.ContainerCreateConverter;
import de.daxu.swamp.api.dto.container.ContainerCreateDTO;
import de.daxu.swamp.api.dto.container.ContainerDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.Project;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( value = "/projects/{projectId}/containers/{containerId}" )
public class ContainerResource {

    @Autowired
    ProjectService projectService;

    @Autowired
    ContainerConverter containerConverter;

    @Autowired
    ContainerCreateConverter containerCreateConverter;

    @RequestMapping( method = RequestMethod.GET )
    public ResponseEntity<ContainerDTO> getContainer( @PathVariable( "projectId" ) String projectId,
                                                      @PathVariable( "containerId" ) String containerId ) {
        return new ResponseEntity<>( containerConverter.toDTO( projectService.getContainer( containerId ) ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.PUT )
    public ResponseEntity<ContainerDTO> putContainer( @PathVariable( "projectId" ) String projectId,
                                                      @PathVariable( "containerId" ) String containerId,
                                                      @RequestBody ContainerCreateDTO dto ) {
        Container oldContainer = projectService.getContainer( containerId );
        Container newContainer = containerCreateConverter.toDomain( dto );

        BeanUtils.copyProperties( newContainer, oldContainer );
        projectService.updateContainer( oldContainer );

        return new ResponseEntity<>( containerConverter.toDTO( oldContainer ), HttpStatus.OK );
    }

    @RequestMapping( method = RequestMethod.DELETE )
    public ResponseEntity deleteContainer( @PathVariable( "projectId" ) String projectId,
                                           @PathVariable( "containerId" ) String containerId ) {
        Project project = projectService.getProject( projectId );
        Container container = projectService.getContainer( containerId );
        projectService.removeContainerFromProject( project, container );
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
