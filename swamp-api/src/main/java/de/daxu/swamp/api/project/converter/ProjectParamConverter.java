package de.daxu.swamp.api.project.converter;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.core.project.Project;
import de.daxu.swamp.core.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectParamConverter implements Converter<String, Project> {

    private final ProjectService projectService;

    @Autowired
    public ProjectParamConverter( ProjectService projectService ) {
        this.projectService = projectService;
    }

    @Override
    public Project convert( String source ) {
        Project project = projectService.getProject( source );

        if( project == null )
            throw new NotFoundException( "Project was not found!" );

        return project;
    }
}
