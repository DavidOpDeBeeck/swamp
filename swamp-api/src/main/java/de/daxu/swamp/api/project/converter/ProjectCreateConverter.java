package de.daxu.swamp.api.project.converter;

import de.daxu.swamp.api.project.dto.ProjectCreateDTO;
import de.daxu.swamp.common.dto.DomainConverter;
import de.daxu.swamp.core.project.Project;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

import static de.daxu.swamp.core.project.Project.Builder.aProject;

@Component
public class ProjectCreateConverter implements DomainConverter<ProjectCreateDTO, Project> {

    @Override
    public Project toDomain( ProjectCreateDTO dto ) {
        return aProject()
                .withName( dto.name )
                .withDescription( dto.description )
                .withCreatedAt( LocalDateTime.now( Clock.systemUTC()) )
                .build();
    }
}
