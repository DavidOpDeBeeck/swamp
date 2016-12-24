package de.daxu.swamp.api.project;

import de.daxu.swamp.api.project.converter.ProjectConverter;
import de.daxu.swamp.api.project.converter.ProjectCreateConverter;
import de.daxu.swamp.api.project.dto.ProjectCreateDTO;
import de.daxu.swamp.api.project.dto.ProjectDTO;
import de.daxu.swamp.common.util.BeanUtils;
import de.daxu.swamp.common.web.response.Response;
import de.daxu.swamp.common.web.response.ResponseFactory;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static de.daxu.swamp.api.project.ProjectResource.PROJECTS_URL;

@RestController
@RequestMapping( PROJECTS_URL )
public class ProjectResource {

    public static final String PROJECTS_URL = "/projects";

    private final ResponseFactory response;
    private final ProjectService projectService;
    private final ProjectConverter projectConverter;
    private final ProjectCreateConverter projectCreateConverter;

    @Autowired
    public ProjectResource( ResponseFactory responseFactory,
                            ProjectService projectService,
                            ProjectConverter projectConverter,
                            ProjectCreateConverter projectCreateConverter ) {
        this.response = responseFactory;
        this.projectService = projectService;
        this.projectConverter = projectConverter;
        this.projectCreateConverter = projectCreateConverter;
    }

    @RequestMapping( method = RequestMethod.GET )
    public Response getAll() {

        List<ProjectDTO> projects = projectService.getAllProjects()
                .stream()
                .map( projectConverter::toDTO )
                .collect( Collectors.toList() );

        return response.success( projects );
    }

    @RequestMapping( method = RequestMethod.POST )
    public Response post( @RequestBody ProjectCreateDTO projectCreateDTO ) {

        Project project = projectCreateConverter.toDomain( projectCreateDTO );
        project = projectService.createProject( project );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path( "/{id}" )
                .buildAndExpand( project.getId() ).toUri();

        return response.created( location );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.GET )
    public Response get( @PathVariable( "projectId" ) Project project ) {

        return response.success( projectConverter.toDTO( project ) );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.PUT )
    public Response put( @PathVariable( "projectId" ) Project projectToUpdate,
                         @RequestBody ProjectCreateDTO updatedProjectDTO ) {

        Project updatedProject = projectCreateConverter.toDomain( updatedProjectDTO );

        BeanUtils.copyProperties( updatedProject, projectToUpdate );
        projectService.updateProject( projectToUpdate );

        return response.success( projectConverter.toDTO( projectToUpdate ) );
    }

    @RequestMapping( value = "/{projectId}", method = RequestMethod.DELETE )
    public Response delete( @PathVariable( "projectId" ) Project project ) {

        projectService.deleteProject( project );
        return response.success();
    }
}
