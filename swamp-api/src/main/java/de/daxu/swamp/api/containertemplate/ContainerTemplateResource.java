package de.daxu.swamp.api.containertemplate;

import de.daxu.swamp.api.containertemplate.converter.ContainerTemplateConverter;
import de.daxu.swamp.api.containertemplate.converter.ContainerTemplateCreateConverter;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateCreateDTO;
import de.daxu.swamp.api.containertemplate.dto.ContainerTemplateDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.containertemplate.ContainerTemplate;
import de.daxu.swamp.core.containertemplate.ContainerTemplateService;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.containertemplate.ContainerTemplateResource.CONTAINER_TEMPLATES_URL;
import static de.daxu.swamp.api.project.ProjectResource.PROJECTS_URL;

@RestController
@RequestMapping(CONTAINER_TEMPLATES_URL)
public class ContainerTemplateResource {

    static final String CONTAINER_TEMPLATES_URL = PROJECTS_URL + "/{projectId}/container-templates";

    private final ResponseFactory response;
    private final ProjectService projectService;
    private final ContainerTemplateService containerTemplateService;
    private final ContainerTemplateConverter containerTemplateConverter;
    private final ContainerTemplateCreateConverter containerTemplateCreateConverter;

    @Autowired
    public ContainerTemplateResource(ResponseFactory responseFactory,
                                     ProjectService projectService,
                                     ContainerTemplateService containerTemplateService,
                                     ContainerTemplateConverter containerTemplateConverter,
                                     ContainerTemplateCreateConverter containerTemplateCreateConverter) {
        this.response = responseFactory;
        this.projectService = projectService;
        this.containerTemplateService = containerTemplateService;
        this.containerTemplateConverter = containerTemplateConverter;
        this.containerTemplateCreateConverter = containerTemplateCreateConverter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Response getAll(@PathVariable("projectId") Project project) {

        List<ContainerTemplateDTO> containers = project.getContainerTemplates()
                .stream()
                .map(containerTemplateConverter::toDTO)
                .collect(Collectors.toList());

        return response.success(containers);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Response post(@PathVariable("projectId") Project project,
                         @Valid @RequestBody ContainerTemplateCreateDTO dto) {

        ContainerTemplate containerTemplate = containerTemplateCreateConverter.toDomain(dto);
        projectService.addContainerTemplateToProject(project.getId(), containerTemplate);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(containerTemplate.getId()).toUri();

        return response.created(location);
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.GET)
    public Response get(@PathVariable("containerId") ContainerTemplate containerTemplate) {

        return response.success(containerTemplateConverter.toDTO(containerTemplate));
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.PUT)
    public Response put(@PathVariable("containerId") ContainerTemplate containerTemplateToUpdate,
                        @Valid @RequestBody ContainerTemplateCreateDTO updatedContainerDTO) {

        ContainerTemplate updatedContainerTemplate = containerTemplateCreateConverter.toDomain(updatedContainerDTO);

        BeanUtils.copyPropertiesIgnoreNull(updatedContainerTemplate, containerTemplateToUpdate);
        containerTemplateService.updateContainerTemplate(containerTemplateToUpdate);

        return response.success(containerTemplateConverter.toDTO(containerTemplateToUpdate));
    }

    @RequestMapping(value = "/{containerId}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("projectId") Project project,
                           @PathVariable("containerId") ContainerTemplate containerTemplate) {

        projectService.removeContainerTemplateToProject(project.getId(), containerTemplate);
        return response.success();
    }
}
