package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.Server;
import de.daxu.swamp.scheduling.write.WriteService;
import de.daxu.swamp.scheduling.write.containerinstance.ContainerInstanceId;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    public final static String SCHEDULING_URL = "/scheduling";

    @Autowired
    private WriteService writeService;

    @Autowired
    private ProjectService projectService;

    @RequestMapping( value = "/container/{containerId}", method = RequestMethod.GET )
    public void schedule( @PathVariable( value = "containerId" ) String containerId ) {
        Container container = projectService.getContainer( containerId );
        writeService.startContainer( ContainerInstanceId.random(), ( Server ) container.getPotentialLocations().iterator().next() );
    }
}
