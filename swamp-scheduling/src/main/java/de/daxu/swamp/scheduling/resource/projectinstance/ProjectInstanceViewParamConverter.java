package de.daxu.swamp.scheduling.resource.projectinstance;

import de.daxu.swamp.common.web.exception.NotFoundException;
import de.daxu.swamp.scheduling.command.projectinstance.ProjectInstanceId;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceQueryService;
import de.daxu.swamp.scheduling.query.projectinstance.ProjectInstanceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectInstanceViewParamConverter implements Converter<String, ProjectInstanceView> {

    private final ProjectInstanceQueryService projectInstanceQueryService;

    @Autowired
    public ProjectInstanceViewParamConverter( ProjectInstanceQueryService projectInstanceQueryService ) {
        this.projectInstanceQueryService = projectInstanceQueryService;
    }

    @Override
    public ProjectInstanceView convert( String source ) {
        ProjectInstanceId id = ProjectInstanceId.from( source );
        ProjectInstanceView view = projectInstanceQueryService.getProjectInstancesViewById( id );

        if( view == null )
            throw new NotFoundException( "Project instance was not found!" );

        return view;
    }
}
