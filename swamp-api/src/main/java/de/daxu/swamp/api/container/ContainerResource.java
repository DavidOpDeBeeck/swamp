package de.daxu.swamp.api.container;

import de.daxu.swamp.api.container.converter.ContainerConverter;
import de.daxu.swamp.api.container.converter.ContainerCreateConverter;
import de.daxu.swamp.api.container.dto.ContainerCreateDTO;
import de.daxu.swamp.api.container.dto.ContainerDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.container.Container;
import de.daxu.swamp.core.container.ContainerService;
import de.daxu.swamp.core.project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.container.ContainerResource.CONTAINERS_URL;
import static de.daxu.swamp.api.project.ProjectResource.PROJECTS_URL;

@RestController
@RequestMapping(CONTAINERS_URL)
public class ContainerResource {

    static final String CONTAINERS_URL = PROJECTS_URL + "/{projectId}/containers";

    private final ResponseFactory response;
    private final ContainerService containerService;
    private final ContainerConverter containerConverter;
    private final ContainerCreateConverter containerCreateConverter;

    @Autowired
    public ContainerResource(ResponseFactory responseFactory,
                             ContainerService containerService,
                             ContainerConverter containerConverter,
                             ContainerCreateConverter containerCreateConverter) {
        this.response = responseFactory;
        this.containerService = containerService;
        this.containerConverter = containerConverter;
        this.containerCreateConverter = containerCreateConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll(@PathVariable("projectId") Project project) {

        List<ContainerDTO> containers = project.getContainers()
                .stream()
                .map(containerConverter::toDTO)
                .collect(Collectors.toList());

        return response.success(containers);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response post(@PathVariable("projectId") Project project,
                         @Valid @RequestBody ContainerCreateDTO dto) {

        Container container = containerCreateConverter.toDomain(dto);
        container = containerService.addContainerToProject(project, container);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(container.getId()).toUri();

        return response.created(location);
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.GET)
    public Response get(@PathVariable("containerId") Container container) {

        return response.success(containerConverter.toDTO(container));
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.PUT)
    public Response put(@PathVariable("containerId") Container containerToUpdate,
                        @Valid @RequestBody ContainerCreateDTO updatedContainerDTO) {

        Container updatedContainer = containerCreateConverter.toDomain(updatedContainerDTO);

        BeanUtils.copyProperties(updatedContainer, containerToUpdate);
        containerService.updateContainer(containerToUpdate);

        return response.success(containerConverter.toDTO(containerToUpdate));
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("projectId") Project project,
                           @PathVariable("containerId") Container container) {

        containerService.removeContainerFromProject(project, container);
        return response.success();
    }
}
