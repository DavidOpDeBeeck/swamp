package de.daxu.swamp.scheduling.resource;

import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.location.*;
import de.daxu.swamp.scheduling.write.ContainerInstanceWriteService;
import de.daxu.swamp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static de.daxu.swamp.scheduling.resource.SchedulingResource.SCHEDULING_URL;

@RestController
@RequestMapping( SCHEDULING_URL )
public class SchedulingResource {

    public final static String SCHEDULING_URL = "/scheduling";

    private final ContainerInstanceWriteService containerInstanceWriteService;
    private final ProjectService projectService;

    @Autowired
    public SchedulingResource( ContainerInstanceWriteService containerInstanceWriteService,
                               ProjectService projectService ) {
        this.containerInstanceWriteService = containerInstanceWriteService;
        this.projectService = projectService;
    }
}
