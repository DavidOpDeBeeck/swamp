package de.daxu.swamp.api.converter.container;

import de.daxu.swamp.api.converter.DomainConverter;
import de.daxu.swamp.api.dto.container.ProjectCreateDTO;
import de.daxu.swamp.core.project.Project;
import org.springframework.stereotype.Component;

import java.util.Date;

import static de.daxu.swamp.core.project.Project.ProjectBuilder.aProject;

@Component
public class ProjectCreateConverter implements DomainConverter<ProjectCreateDTO, Project> {

    @Override
    public Project toDomain( ProjectCreateDTO dto ) {
        return aProject()
                .withName( dto.name )
                .withDescription( dto.description )
                .createdAt( new Date() )
                .build();
    }
}
